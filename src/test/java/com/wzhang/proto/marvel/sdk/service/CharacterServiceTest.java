package com.wzhang.proto.marvel.sdk.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.wzhang.proto.marvel.sdk.dto.CharacterDTO;
import com.wzhang.proto.marvel.sdk.dto.GetCharacterQuery;
import com.wzhang.proto.marvel.sdk.dto.GetCharactersData;
import com.wzhang.proto.marvel.sdk.dto.GetCharactersResponseDTO;
import com.wzhang.proto.marvel.sdk.dto.SummaryDTO;
import com.wzhang.proto.marvel.sdk.dto.UrlDTO;
import com.wzhang.proto.marvel.sdk.enumeration.Endpoint;
import com.wzhang.proto.marvel.sdk.util.HashingUtil;
import com.wzhang.proto.marvel.sdk.util.WebUtil;

public class CharacterServiceTest {

	@Mock
	private WebUtil webUtil;

	@Mock
	private CaffeineCacheManager caffeineCacheManager;

	@Mock
	private CacheService cacheService;

	private CharacterService characterService;

	private static final String GATEWAY_URL = "http://127.0.0.1/";

	private static final String PRIVATE_KEY = "34589303df890ds8d90s8";

	private static final String PUBLIC_KEY = "9458049VDFVUD9F0VD8V90D8F90F0FD098D";

	private static final long TIMESTAMP = System.currentTimeMillis();

	private static final String API_HASH = HashingUtil.createHashString(TIMESTAMP + PRIVATE_KEY + PUBLIC_KEY);

	private static final String ETAG = "r89fic9dfvu8x9d8s90ifv90dv";

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		characterService = new CharacterService(webUtil, caffeineCacheManager, cacheService, GATEWAY_URL, PRIVATE_KEY,
				PUBLIC_KEY);
	}

//	@Test
//	void testGetCharactersV1() {
//		final GetCharacterQuery query = GetCharacterQuery.builder().events(1).name("spidey").series(2).build();
//		final GetCharactersResponseDTO dto = new GetCharactersResponseDTO();
//		dto.setEtag(ETAG);
//
//		final NoOpCache cache = new NoOpCache("characters");
//		cache.put(query.getHash(), dto);
//
//		when(caffeineCacheManager.getCache(CacheSetting.CHARACTERS.getCacheName())).thenReturn(cache);
//
//		final GetCharactersResponseDTO cachedResponse = assertDoesNotThrow(
//				() -> characterService.getCharactersV1(query, Boolean.FALSE));
//
//		assertNotNull(cachedResponse);
//		assertEquals(ETAG, cachedResponse.getEtag());
//
//		final GetCharactersResponseDTO fetched = new GetCharactersResponseDTO();
//		dto.setEtag(API_HASH);
//
//		when(webUtil.exchange(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
//				.thenReturn(new ResponseEntity<>(fetched, HttpStatusCode.valueOf(200)));
//
//		final GetCharactersResponseDTO fetchedResponse = assertDoesNotThrow(
//				() -> characterService.getCharactersV1(query, Boolean.TRUE));
//
//		assertNotNull(fetchedResponse);
//		assertEquals(API_HASH, fetchedResponse.getEtag());
//	}

	@Test
	void testFetchCharactersV1() throws Exception {
		final GetCharacterQuery query = GetCharacterQuery.builder().events(1).name("spidey").series(2).build();

		final CharacterDTO characterDTO = new CharacterDTO();
		characterDTO.setDescription("desc");
		characterDTO.setComics(new SummaryDTO());
		characterDTO.setUrls(List.of(new UrlDTO()));
		characterDTO.setName("spidey");

		final GetCharactersData data = new GetCharactersData();
		data.setResults(List.of(characterDTO));

		final GetCharactersResponseDTO dto = new GetCharactersResponseDTO();
		dto.setData(data);
		dto.setEtag(ETAG);

		when(webUtil.exchange(GATEWAY_URL, Endpoint.CHARACTERS_GET_V1.getPath(), null, PUBLIC_KEY, API_HASH, TIMESTAMP,
				query.toParamsMap(), null, HttpMethod.GET, new ParameterizedTypeReference<GetCharactersResponseDTO>() {
				})).thenReturn(new ResponseEntity<>(dto, HttpStatusCode.valueOf(200)));

		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> characterService.fetchCharactersV1(null, TIMESTAMP));

		assertEquals("query cannot be null", exception.getMessage());

		final GetCharactersResponseDTO response = assertDoesNotThrow(
				() -> characterService.fetchCharactersV1(query, TIMESTAMP));
		assertNotNull(response);
		assertEquals(ETAG, response.getEtag());

		final GetCharactersData responseData = response.getData();
		assertNotNull(responseData);

		final List<CharacterDTO> characters = responseData.getResults();
		assertNotNull(characters);
		assertEquals(1, characters.size());

		final CharacterDTO character = characters.get(0);
		assertNotNull(character);
		assertEquals("spidey", character.getName());
		assertEquals("desc", character.getDescription());
		assertNotNull(character.getComics());
		assertNotNull(character.getUrls());
	}
}
