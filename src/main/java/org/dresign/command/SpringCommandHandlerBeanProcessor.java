package org.dresign.command;

import org.dresign.bus.Bus;
import org.dresign.bus.HandlerBeanProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringCommandHandlerBeanProcessor implements DestructionAwareBeanPostProcessor, ApplicationContextAware{

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
		HandlerBeanProcessor processor = new HandlerBeanProcessor(bus,bean,CommandHandler.class);
		processor.analyseCandidateForSubscribe();
		processor = new HandlerBeanProcessor(bus,bean,AsyncCommandHandler.class);
		processor.analyseCandidateForSubscribe();
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public void postProcessBeforeDestruction(Object bean, String beanName)
			throws BeansException {
		HandlerBeanProcessor processor = new HandlerBeanProcessor(bus,bean,CommandHandler.class);
		processor.analyseCandidateForUnSubscribe();
		processor = new HandlerBeanProcessor(bus,bean,AsyncCommandHandler.class);
		processor.analyseCandidateForUnSubscribe();
	}


	
	private Bus getCommandBus() {
		return bus;
	}
	
	public void setBus(Bus bus) {
		this.bus=bus;
	}

}
