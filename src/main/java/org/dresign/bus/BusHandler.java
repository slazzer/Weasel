package org.dresign.bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BusHandler implements Bus {

	protected final ConcurrentMap<String, List<Map<Object, List<Method>>>> subscriptions = new ConcurrentHashMap<String, List<Map<Object, List<Method>>>>();

	@Override
	public void dispatch(Object event) {
		List<Map<Object, List<Method>>> objects = findObjectFor(event);
		if (objects != null) {
			for (Map<Object, List<Method>> objet : objects) {
				for (Map.Entry<Object, List<Method>> entry : objet.entrySet()) {
					Object handler = entry.getKey();
					for (Method m : entry.getValue()) {
						try {
							realDispatch(event, handler, m);
						} catch (Throwable e) {
							throw new RuntimeException(
									"Error While dispatching event", e);
						}
					}
				}
			}
		}
	}

	protected void realDispatch(Object event, Object handler, Method m)
			throws IllegalAccessException, InvocationTargetException {
		m.invoke(handler, event);
	}

	protected List<Map<Object, List<Method>>> findObjectFor(Object event) {
		final List<Map<Object, List<Method>>> handlers = subscriptions
				.get(event.getClass().getCanonicalName());
		return handlers;
	}

	@Override
	public <C> void subscribe(Class<C> domainEventType, Object handler, Method m) {
		List<Map<Object, List<Method>>> clazz = subscriptions
				.get(domainEventType.getCanonicalName());
		if (clazz == null) {
			clazz = new ArrayList<Map<Object, List<Method>>>();
			subscriptions.put(domainEventType.getCanonicalName(), clazz);
		}
		boolean found = false;
		List<Method> methodList = null;
		for (Map<Object, List<Method>> entry : clazz) {
			for (Entry<Object, List<Method>> element : entry.entrySet()) {
				Object objetHandler = element.getKey();
				if (objetHandler != null) {
					if (objetHandler.getClass().isInstance(handler)) {
						found = true;
						methodList = element.getValue();
					}
				}
			}
		}
		if (!found) {
			Map<Object, List<Method>> map = new HashMap<Object, List<Method>>();
			methodList = new ArrayList<Method>();
			map.put(handler, methodList);
			clazz.add(map);
		}
		methodList.add(m);
	}

	@Override
	public <C> void unsubscribe(Class<C> domainEventType, Object handler) {
		List<Map<Object, List<Method>>> clazz = subscriptions
				.get(domainEventType);
		List<Object> referencesToRemove = new ArrayList<Object>();
		if (clazz != null) {
			for (Map<Object, List<Method>> elementList : clazz) {
				for (Entry<Object, List<Method>> element : elementList
						.entrySet()) {
					Object objetHandler = element.getKey();
					if (objetHandler != null) {
						if (objetHandler.getClass().isInstance(handler)) {
							referencesToRemove.add(element.getKey());
						}
					} else {
						referencesToRemove.add(element.getKey());
					}

				}
			}
			for (Object element : referencesToRemove) {
				clazz.remove(element);
			}
		}
	}

}
