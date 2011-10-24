package org.dresign.bus.redis;

import org.springframework.scheduling.annotation.Scheduled;

public interface Consumer {

	@Scheduled(fixedDelay = 200)
	public abstract void handleEvent();

}