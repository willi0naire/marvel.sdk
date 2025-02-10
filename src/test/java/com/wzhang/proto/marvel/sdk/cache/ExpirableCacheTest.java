package com.wzhang.proto.marvel.sdk.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class ExpirableCacheTest {
	private static final ExpirableCache<String, String> TEST_CACHE = new ExpirableCache<>(60, TimeUnit.SECONDS);
	private static final String TEST_KEY = "key";
	private static final String TEST_VALUE = "value";
	private static final String TEST_KEY_NOT_FOUND = "key_not_found";

	@Test
	void testGet() {
		TEST_CACHE.putIfAbsent(TEST_KEY, TEST_VALUE);
		assertEquals(TEST_VALUE, TEST_CACHE.get(TEST_KEY));
		assertNull(TEST_CACHE.get(TEST_KEY_NOT_FOUND));
	}

	@Test
	void testPutIfAbsent() {
		TEST_CACHE.putIfAbsent(TEST_KEY, TEST_VALUE);
		assertEquals(TEST_VALUE, TEST_CACHE.get(TEST_KEY));
		assertNull(TEST_CACHE.get(TEST_KEY_NOT_FOUND));
	}

	@Test
	void testRemove() {
		TEST_CACHE.putIfAbsent(TEST_KEY, TEST_VALUE);
		assertEquals(TEST_VALUE, TEST_CACHE.get(TEST_KEY));
		TEST_CACHE.remove(TEST_KEY);
		assertNull(TEST_CACHE.get(TEST_KEY));
	}
}
