package org.dresign.bus.redis;

public interface Publisher {

	public abstract void pushEvent(Object event);

}