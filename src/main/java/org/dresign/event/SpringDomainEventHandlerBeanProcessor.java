package org.dresign.event;
import org.dresign.bus.Bus;
import org.dresign.bus.HandlerBeanProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringDomainEventHandlerBeanProcessor implements DestructionAwareBeanPostProcessor, ApplicationContextAware{
		private ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.applicationContext=context;
		
	}
	private Bus bus;

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		HandlerBeanProcessor processor = new HandlerBeanProcessor(bus,bean,DomainEventHandler.class);
		processor.analyseCandidateForSubscribe();
		processor = new HandlerBeanProcessor(bus,bean,AsyncDomainEventHandler.class);
		processor.analyseCandidateForSubscribe();
		return bean;
	}

	@Override
	public void postProcessBeforeDestruction(Object bean, String beanName)
			throws BeansException {
		HandlerBeanProcessor processor = new HandlerBeanProcessor(bus,bean,DomainEventHandler.class);
		processor.analyseCandidateForUnSubscribe();
		processor = new HandlerBeanProcessor(bus,bean,AsyncDomainEventHandler.class);
		processor.analyseCandidateForUnSubscribe();
	}

	
	private Bus getDomainEventBus() {
		return bus;
	}
	
	public void setBus(Bus bus) {
		this.bus=bus;
	}

}
