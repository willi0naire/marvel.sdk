package com.wzhang.proto.marvel.sdk.enumeration;

import java.util.concurrent.TimeUnit;

/**
 * enum used to define different caches
 */
public enum CacheSetting {
	CHARACTERS("characters", 2, TimeUnit.MINUTES),

	;

	private String cacheName;

	private int ttl;

	private TimeUnit timeUnit;

	CacheSetting(final String cacheName, final int ttl, final TimeUnit timeUnit) {
		this.cacheName = cacheName;
		this.ttl = ttl;
		this.timeUnit = timeUnit;

	}

	public String getCacheName() {
		return cacheName;
	}

	public int getTTL() {
		return ttl;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}
}
