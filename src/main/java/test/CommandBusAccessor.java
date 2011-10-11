package test;

import org.dresign.bus.Bus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

@Service
public class CommandBusAccessor implements BeanFactoryAware{
	private static BeanFactory beanFactory;
	@Override
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		this.beanFactory=arg0;
		
	}
	private static Bus accessor;
	public static Bus bus(){
		if(accessor==null && beanFactory!=null){
			accessor=(Bus) beanFactory.getBean("commandBus");
		}
		return accessor;
	}
}
