package com.wzhang.proto.marvel.sdk.cache;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExpirableCache<K, V> implements MyCache<K, V> {
	private final String HIT = "HIT";
	private final String MISS = "MISS";
	private final ConcurrentMap<K, V> cache = new ConcurrentHashMap<>();
	private final ConcurrentMap<K, Long> expirationTimes = new ConcurrentHashMap<>();
	private final ConcurrentMap<String, Long> hitMissMap = new ConcurrentHashMap<>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final long expirationDuration;
	private final TimeUnit timeUnit;

	public ExpirableCache(long expirationDuration, TimeUnit timeUnit) {
		this.expirationDuration = expirationDuration;
		this.timeUnit = timeUnit;
		hitMissMap.put(HIT, 0L);
		hitMissMap.put(MISS, 0L);
		scheduler.scheduleAtFixedRate(this::removeExpiredEntries, expirationDuration, expirationDuration, timeUnit);
	}

	@Override
	public V get(K key) {
		final var V = cache.get(key);
		hitMissMap.merge(null == V ? MISS : HIT, 1L, Long::sum);
		return cache.get(key);
	}

	@Override
	public boolean contains(K key) {
		return cache.containsKey(key);
	}

	@Override
	public V put(K key, V value) {
		expirationTimes.put(key, System.nanoTime() + timeUnit.toNanos(expirationDuration));
		return cache.put(key, value);
	}

	@Override
	public V putIfAbsent(K key, V value) {
		expirationTimes.putIfAbsent(key, System.nanoTime() + timeUnit.toNanos(expirationDuration));
		return cache.putIfAbsent(key, value);
	}

	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		expirationTimes.putIfAbsent(key, System.nanoTime() + timeUnit.toNanos(expirationDuration));
		return cache.computeIfAbsent(key, k -> {
			V value = mappingFunction.apply(k);
			expirationTimes.put(k, System.nanoTime());
			return value;
		});
	}

	@Override
	public boolean remove(K key) {
		final var V = cache.remove(key);
		expirationTimes.remove(key);
		hitMissMap.put(HIT, 0L);
		hitMissMap.put(MISS, 0L);
		if (null != V)
			System.out.println("removed: " + key);
		return null != V;
	}

	private void removeExpiredEntries() {
		System.out.println("evicting cache...");
		for (K key : expirationTimes.keySet()) {
			if (null == expirationTimes.get(key) || expirationTimes.get(key) <= System.nanoTime()) {
				System.out.println(String.format("%s removing expired cache with key=%s result=%s", LocalDateTime.now(),
						key, remove(key)));
			}
		}
		System.out.println("done evicting cache...");
	}

	public void shutdown() {
		scheduler.shutdown();
	}

	@Override
	public long getHit() {
		return hitMissMap.get(HIT);
	}

	@Override
	public long getMiss() {
		return hitMissMap.get(MISS);
	}
}
