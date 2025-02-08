package com.wzhang.proto.marvel.sdk.cache;

import java.util.function.Function;

public interface MyCache<K, V> {
	public V get(K key);

	public boolean contains(K key);

	public V put(K key, V value);

	public V putIfAbsent(K key, V value);

	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction);

	public boolean remove(K key);

	public long getHit();

	public long getMiss();
}
