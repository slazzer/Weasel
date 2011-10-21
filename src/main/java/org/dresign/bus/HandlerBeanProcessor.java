package org.dresign.bus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.dresign.event.DomainEvent;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;

public class HandlerBeanProcessor<C extends Annotation> {
	private Bus bus;
	private Object handlerCandidate;
	private Class<C> annotationClazz;

	public HandlerBeanProcessor(Bus bus, Object handlerCandidate,
			Class<C> annotation) {
		this.bus = bus;
		this.handlerCandidate = handlerCandidate;
		this.annotationClazz = annotation;
	}

	private List<Method> findAnnotatedMethods(Class<?> clazz,
			Class<C> annotationClazz) {
		List<Method> methods = new ArrayList<Method>();
		for (Method m : getAllDeclaredMethods(clazz)) {
			C a = getAnnotation(m, annotationClazz);
			if (a != null) {
				methods.add(m);
			}
		}
		return methods;
	}

	@SuppressWarnings("unchecked")
	C getAnnotation(Method m, Class<C> annotationType) {
		for (Annotation a : m.getDeclaredAnnotations()) {
			if (a.annotationType() == annotationType) {
				return (C) a;
			}
		}
		return null;
	}

	Method[] getAllDeclaredMethods(Class<?> leafClass)
			throws IllegalArgumentException {
		final List<Method> list = new ArrayList<Method>(32);
		ReflectionUtils.doWithMethods(leafClass, new MethodCallback() {
			@Override
			public void doWith(Method method) throws IllegalArgumentException,
					IllegalAccessException {
				list.add(method);
			}
		});
		return (Method[]) list.toArray(new Method[list.size()]);
	}

	public void analyseCandidateForSubscribe() {

		List<Method> methods = findAnnotatedMethods(
				handlerCandidate.getClass(), annotationClazz);
		for (Method m : methods) {
			if (m.getParameterTypes().length != 1) {
				throw new RuntimeException(
						"Your Handler should only have one parameter");
			}
			if (m.getParameterTypes()[0].getAnnotations().length != 1) {
				if (!m.getParameterTypes()[0].getAnnotations()[0]
						.annotationType().isAssignableFrom(DomainEvent.class)) {
					throw new RuntimeException(
							"Your Handler should only have one parameter of Type :"
									+ DomainEvent.class.getSimpleName());
				}
			}
			Class domainEventType = m.getParameterTypes()[0];
			bus.subscribe(domainEventType, handlerCandidate, m);
		}
	}

	public void analyseCandidateForUnSubscribe() {
		List<Method> methods = findAnnotatedMethods(
				handlerCandidate.getClass(), annotationClazz);
		for (Method m : methods) {
			if (m.getParameterTypes().length != 1) {
				throw new RuntimeException(
						"Your Handler should only have one parameter");
			}
			if (m.getParameterTypes()[0].getAnnotations().length != 1) {
				if (!m.getParameterTypes()[0].getAnnotations()[0]
						.annotationType().isAssignableFrom(DomainEvent.class)) {
					throw new RuntimeException(
							"Your Handler should only have one parameter of Type :"
									+ DomainEvent.class.getSimpleName());
				}
			}
			Class domainEventType = m.getParameterTypes()[0];
			bus.unsubscribe(domainEventType, handlerCandidate);
		}
	}

}