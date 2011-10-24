package org.dresign.bus.redis;


public class Dispatcher{
	Object event;
	String handlerClassFullName;
	String methodName;
	public Dispatcher(Object event, String handlerClassFullName, String methodName) {
		super();
		this.event = event;
		this.handlerClassFullName = handlerClassFullName;
		this.methodName = methodName;
	}
}