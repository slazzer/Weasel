package org.dresign.bus;
import java.lang.reflect.Method;



public interface Bus {

		void  dispatch(Object event) ;

	    <C> void subscribe(Class<C> domainEventType, Object handler, Method method);

	    <C> void unsubscribe(Class<C> domainEventType, Object handler);
	
}
