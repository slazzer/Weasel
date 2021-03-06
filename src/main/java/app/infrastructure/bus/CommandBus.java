package app.infrastructure.bus;

import org.dresign.bus.Bus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

@Service
public class CommandBus implements BeanFactoryAware {

	private static Bus bus;

	private static BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public static Bus bus() {
		if (bus == null && beanFactory != null) {
			bus = (Bus) beanFactory.getBean("veggieCommandBus");
		}
		return bus;
	}

}
