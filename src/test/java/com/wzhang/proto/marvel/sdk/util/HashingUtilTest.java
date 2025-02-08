package com.wzhang.proto.marvel.sdk.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.wzhang.proto.marvel.sdk.dto.GetCharacterQuery;
import com.wzhang.proto.marvel.sdk.dto.GetCharactersSortBy;
import com.wzhang.proto.marvel.sdk.enumeration.GetCharactersSortField;
import com.wzhang.proto.marvel.sdk.enumeration.SortDirection;

public class HashingUtilTest {

	private static final String SECRET = "485u930i4o2pekmnfhir9w4ipo3kelmdncheij;orkw4denf";
	private static final long CURRENT_MILLIS = System.currentTimeMillis();

	@Test
	void testcreateHashBytes() {
		final byte[] hash1 = HashingUtil.createHashBytes(SECRET + CURRENT_MILLIS);
		final byte[] hash2 = HashingUtil.createHashBytes(SECRET + CURRENT_MILLIS);
		final byte[] hash3 = HashingUtil.createHashBytes(SECRET + System.currentTimeMillis());

		assertArrayEquals(hash1, hash2);
		assertFalse(Arrays.equals(hash1, hash3));
	}

	@Test
	void testcreateHashString() {
		final String hash1 = HashingUtil.createHashString(SECRET + CURRENT_MILLIS);
		final String hash2 = HashingUtil.createHashString(SECRET + CURRENT_MILLIS);
		final String hash3 = HashingUtil.createHashString(SECRET + System.currentTimeMillis());

		assertEquals(hash1, hash2);
		assertFalse(hash2.equals(hash3));
	}

	@Test
	void testGetHashCode() {
		final GetCharacterQuery query1 = GetCharacterQuery.builder().comics(2).events(3).limit(2).name(SECRET)
				.nameStartsWith("48").offset(12).series(12)
				.sortBys(List.of(new GetCharactersSortBy(GetCharactersSortField.MODIFIED_SINCE, SortDirection.DESC)))
				.build();
		final GetCharacterQuery query2 = GetCharacterQuery.builder().comics(2).events(3).limit(2).name(SECRET)
				.nameStartsWith("48").offset(12).series(12)
				.sortBys(List.of(new GetCharactersSortBy(GetCharactersSortField.MODIFIED_SINCE, SortDirection.DESC)))
				.build();
		final GetCharacterQuery query3 = GetCharacterQuery.builder().comics(2).events(3).limit(2).name(SECRET)
				.nameStartsWith("45").offset(12).series(12)
				.sortBys(List.of(new GetCharactersSortBy(GetCharactersSortField.MODIFIED_SINCE, SortDirection.DESC)))
				.build();
		final int hash1 = HashingUtil.getHashCode(query1, query1.toParamsMap().toString());
		final int hash2 = HashingUtil.getHashCode(query2, query2.toParamsMap().toString());
		final int hash3 = HashingUtil.getHashCode(query3, query3.toParamsMap().toString());

		assertEquals(hash1, hash2);
		assertNotEquals(hash1, hash3);
	}
}
