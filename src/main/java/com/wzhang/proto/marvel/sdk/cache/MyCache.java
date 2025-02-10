package com.wzhang.proto.marvel.sdk.cache;

import java.util.Map;
import java.util.function.Function;

public interface MyCache<K, V> {
	public V get(K key);

	public V putIfAbsent(K key, V value);

	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction);

	public boolean remove(K key);

	public long getHit();

	public long getMiss();

	public Map<String, Long> getStats();
}
