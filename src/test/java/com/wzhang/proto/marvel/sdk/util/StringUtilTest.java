package com.wzhang.proto.marvel.sdk.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringUtilTest {
	@Test
	void testIsEmpty() {
		assertTrue(StringUtil.isEmpty(""));
		assertTrue(StringUtil.isEmpty(null));
		assertTrue(StringUtil.isEmpty("  "));
		assertFalse(StringUtil.isEmpty("empty"));
	}

	@Test
	void testIsNotEmpty() {
		assertFalse(StringUtil.isNotEmpty(""));
		assertFalse(StringUtil.isNotEmpty(null));
		assertFalse(StringUtil.isNotEmpty("  "));
		assertTrue(StringUtil.isNotEmpty("empty"));
	}
}
