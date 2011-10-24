package org.dresign.bus.redis;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class TaskExecutor implements ApplicationContextAware{
	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context=context;
	}

	private static int POOL_SIZE = 4;

	Queue<Dispatcher> queue = new ConcurrentLinkedQueue<Dispatcher>();
	
	ExecutorService threadPoolExecutor ;
	
	public TaskExecutor() {
		threadPoolExecutor = Executors.newFixedThreadPool(POOL_SIZE);
	}

	public TaskExecutor(int poolSize) {
		threadPoolExecutor = Executors.newFixedThreadPool(poolSize);
	}
	
	public void doDispatch(Dispatcher dispatcher) {
		queue.add(dispatcher);
		threadPoolExecutor.submit(new Runnable(){

			@Override
			public void run() {
				Dispatcher dispatcher = queue.poll();
				try {
					Class<Object> clazz = (Class<Object>) this.getClass().getClassLoader().loadClass(dispatcher.handlerClassFullName);
					Map<String, Object> mapOfBeans = context.getBeansOfType(clazz );
					Object handler = mapOfBeans.values().toArray()[0];
					Method m=null;
					m=handler.getClass().getDeclaredMethod(dispatcher.methodName, dispatcher.event.getClass());
					m.invoke(handler, dispatcher.event);
				} catch (Throwable e) {
					e.printStackTrace();
					throw new RuntimeException(
							"Error While dispatching event", e);
				}
			}});
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
