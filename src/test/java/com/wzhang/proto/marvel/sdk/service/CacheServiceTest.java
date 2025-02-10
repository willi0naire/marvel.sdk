package com.wzhang.proto.marvel.sdk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.wzhang.proto.marvel.sdk.dto.GetCharactersResponseDTO;

public class CacheServiceTest {
	private final CacheService cacheService = CacheService.getInstance();

	@Test
	void testGetInstance() {
		assertNotNull(cacheService);
	}

	@Test
	void testSetCharacters() {
		final String key = "38248234234354";
		final GetCharactersResponseDTO dto = new GetCharactersResponseDTO();
		dto.setEtag("4243294234830483");
		final GetCharactersResponseDTO cached = cacheService.setCharacters(key, dto);
		assertNotNull(cached);
		assertEquals(dto.getEtag(), cached.getEtag());
	}

	@Test
	void testGetCharacters() {
		final String key = "38248234234354";
		final GetCharactersResponseDTO dto = new GetCharactersResponseDTO();
		dto.setEtag("4243294234830483");
		cacheService.setCharacters(key, dto);

		final GetCharactersResponseDTO cached = cacheService.getCharacters(key);
		assertNotNull(cached);
		assertEquals(dto.getEtag(), cached.getEtag());

		assertNull(cacheService.getCharacters("324534"));
	}

	@Test
	void testHasCharacters() {
		final String key = "38248234234354";
		final GetCharactersResponseDTO dto = new GetCharactersResponseDTO();
		cacheService.setCharacters(key, dto);

		assertNotNull(cacheService.getCharacters(key));
		assertNull(cacheService.getCharacters("342543"));
	}

	@Test
	void testEvictCharacters() {
		final String key = "38248234234354";
		final GetCharactersResponseDTO dto = new GetCharactersResponseDTO();
		cacheService.setCharacters(key, dto);
		assertNotNull(cacheService.getCharacters(key));
		cacheService.evictCharacters(key);
		assertNull(cacheService.getCharacters(key));
	}

}
