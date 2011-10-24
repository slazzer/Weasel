package org.dresign.bus.simple;

import java.lang.reflect.Method;

public class Dispatcher{
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