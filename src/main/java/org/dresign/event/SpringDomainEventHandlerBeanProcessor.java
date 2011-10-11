package org.dresign.event;
import org.dresign.bus.Bus;
import org.dresign.bus.HandlerBeanProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;


public class SpringDomainEventHandlerBeanProcessor implements DestructionAwareBeanPostProcessor{


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
