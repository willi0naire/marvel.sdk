package com.wzhang.proto.marvel.sdk.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wzhang.proto.marvel.sdk.dto.GetCharacterQuery;
import com.wzhang.proto.marvel.sdk.dto.GetCharactersResponseDTO;

public class CharacterServiceTest {

	private static final String GATEWAY_URL = "https://gateway.marvel.com";
	private static final String PRIVATE_KEY = "08bbeb790b25357d9401c1078be190e2bd020235";
	private static final String PUBLIC_KEY = "4ccb4b80e90da7d1d9e3fb682b336cc3";
	private CharacterService characterService;

	@BeforeEach
	public void setUp() {
		characterService = new CharacterService(GATEWAY_URL, PRIVATE_KEY, PUBLIC_KEY);
	}

	@Test
	void testGetCharactersV1() {
		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> characterService.getCharactersV1(null, false));
		assertEquals("query cannot be null", exception.getMessage());

		final GetCharacterQuery query = GetCharacterQuery.builder().nameStartsWith("s").build();
		final GetCharactersResponseDTO fetchResponse = assertDoesNotThrow(
				() -> characterService.getCharactersV1(query, false));
		assertNotNull(fetchResponse);
		assertNotNull(fetchResponse.getData());
	}
}
