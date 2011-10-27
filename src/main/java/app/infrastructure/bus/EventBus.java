package app.infrastructure.bus;

import org.dresign.bus.Bus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

@Service
public class EventBus implements BeanFactoryAware {
	
	private static BeanFactory beanFactory;
	private static Bus bus;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public static Bus bus() {
		if (bus == null && beanFactory != null) {
			bus = (Bus) beanFactory.getBean("veggieEventBus");
		}
		return bus;
	}
	
	public static void notifyEvent(Object event) {
		bus().dispatch(event);
	}

}
