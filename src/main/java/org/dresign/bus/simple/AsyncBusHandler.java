package org.dresign.bus.simple;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.dresign.bus.AsyncBusTransactionalHandler;
import org.dresign.event.AsyncDomainEventHandler;


public class AsyncBusHandler extends BusHandler implements AsyncBusTransactionalHandler {


	
	private static int POOL_SIZE = 4;

	Queue<Dispatcher> queue = new ConcurrentLinkedQueue<Dispatcher>();
	
	ExecutorService threadPoolExecutor ;
	

	public AsyncBusHandler() {
		threadPoolExecutor = Executors.newFixedThreadPool(POOL_SIZE);
	}

	public AsyncBusHandler(int poolSize) {
		threadPoolExecutor = Executors.newFixedThreadPool(poolSize);
	}
	
	public void asyncDispatch(Dispatcher dispatcher) {
		queue.add(dispatcher);
		threadPoolExecutor.submit(new Runnable(){

			@Override
			public void run() {
				Dispatcher dispatcher = queue.poll();
				try {
					dispatcher.m.invoke(dispatcher.handler, dispatcher.event);
				} catch (Throwable e) {
					e.printStackTrace();
					throw new RuntimeException(
							"Error While dispatching event", e);
				}
			}});
	}


	@Override
	protected void realDispatch(Object event, Object handler, Method m)
			throws IllegalAccessException, InvocationTargetException {
		if(m.getAnnotations()[0].annotationType().isAssignableFrom(AsyncDomainEventHandler.class)){
			List<Dispatcher> list = elementToDispatch.get();
			if(list==null){
				asyncDispatch(new Dispatcher(event,handler,m));
			}else{
				list.add(new Dispatcher(event,handler,m));
			}
		}else{
			super.realDispatch(event, handler, m);
		}
	}
	
	public boolean awaitTermination(long time, TimeUnit unit) throws InterruptedException{
		return threadPoolExecutor.awaitTermination(time, unit);
	}
	
	public void shutDown(){
		this.threadPoolExecutor.shutdown();
	}

	public List<Runnable> shutDownNow(){
		return this.threadPoolExecutor.shutdownNow();
	}

	ThreadLocal<List<Dispatcher>> elementToDispatch = new ThreadLocal<List<Dispatcher>>();
	
	@Override
	public void endTransaction() {
		elementToDispatch.get().clear();
		elementToDispatch.remove();
	}

	@Override
	public void startNewTransaction() {
		elementToDispatch.set(new ArrayList<Dispatcher>());
	}

	@Override
	public void performTransaction() {
		List<Dispatcher> eventADispatcher = elementToDispatch.get();
		for(Dispatcher element:eventADispatcher){
			asyncDispatch(element);
		}
	}

	
}
