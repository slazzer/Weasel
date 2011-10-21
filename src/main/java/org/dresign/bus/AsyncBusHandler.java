package org.dresign.bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.dresign.event.AsyncDomainEventHandler;


public class AsyncBusHandler extends BusHandler {

	class Dispatcher{
		Object event;
		Object handler;
		Method m;
		public Dispatcher(Object event, Object handler, Method m) {
			super();
			this.event = event;
			this.handler = handler;
			this.m = m;
		}
	}
	
	private static int POOL_SIZE = 4;

	Queue<Dispatcher> queue = new ConcurrentLinkedQueue<Dispatcher>();
	
	ExecutorService threadPoolExecutor ;
	

	public AsyncBusHandler() {
		threadPoolExecutor = Executors.newFixedThreadPool(POOL_SIZE);
	}

	public AsyncBusHandler(int poolSize) {
		threadPoolExecutor = Executors.newFixedThreadPool(poolSize);
	}
	
	public void asyncDispatch(Object event, Object handler, Method m) {
		queue.add(new Dispatcher(event,handler,m));
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
				asyncDispatch(event,handler,m);
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

	
}
