package org.dresign.bus.redis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.dresign.bus.AsyncBusTransactionalHandler;
import org.dresign.bus.simple.BusHandler;
import org.dresign.event.AsyncDomainEventHandler;
import org.springframework.beans.factory.annotation.Autowired;


public class AsyncBusHandler extends BusHandler implements AsyncBusTransactionalHandler  {

	@Autowired
	Publisher publisher;

	@Override
	protected void realDispatch(Object event, Object handler, Method m)
			throws IllegalAccessException, InvocationTargetException {
		if(m.getAnnotations()[0].annotationType().isAssignableFrom(AsyncDomainEventHandler.class)){
			List<Dispatcher> list = elementToDispatch.get();
			if(list==null){
				doDispatch(new Dispatcher(event,(handler.getClass().getName().contains("CGLIB")?handler.getClass().getSuperclass().getCanonicalName():handler.getClass().getCanonicalName()),m.getName()));
			}else{
				list.add(new Dispatcher(event,(handler.getClass().getName().contains("CGLIB")?handler.getClass().getSuperclass().getCanonicalName():handler.getClass().getCanonicalName()),m.getName()));
			}
		}else{
			super.realDispatch(event, handler, m);
		}
	}
	


	private void doDispatch(Dispatcher dispatcher) {
		publisher.pushEvent(dispatcher);
	}



	ThreadLocal<List<Dispatcher>> elementToDispatch = new ThreadLocal<List<Dispatcher>>();
	
	public void endTransaction() {
		elementToDispatch.get().clear();
		elementToDispatch.remove();
	}

	public void startNewTransaction() {
		elementToDispatch.set(new ArrayList<Dispatcher>());
	}

	public void performTransaction() {
		List<Dispatcher> eventADispatcher = elementToDispatch.get();
		for(Dispatcher element:eventADispatcher){
			doDispatch(element);
		}
	}

	
}
