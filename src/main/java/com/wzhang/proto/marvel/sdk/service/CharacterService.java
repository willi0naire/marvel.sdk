package com.wzhang.proto.marvel.sdk.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wzhang.proto.marvel.sdk.dto.GetCharacterQuery;
import com.wzhang.proto.marvel.sdk.dto.GetCharactersResponseDTO;
import com.wzhang.proto.marvel.sdk.enumeration.CacheSetting;
import com.wzhang.proto.marvel.sdk.enumeration.Endpoint;
import com.wzhang.proto.marvel.sdk.util.HashingUtil;
import com.wzhang.proto.marvel.sdk.util.WebUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * service to deal with character related operations
 */
@Slf4j
@Service
public class CharacterService {

	private final WebUtil webUtil;
	private final CaffeineCacheManager caffeineCacheManager;
	private final CacheService cacheService;
	private final String marvelApiGatewayUrl;
	private final String marvelApiKeyPrivate;
	private final String marvelApiKeyPublic;

	public CharacterService(final @Autowired WebUtil webUtil,
			final @Autowired CaffeineCacheManager caffeineCacheManager, final @Autowired CacheService cacheService,
			final @Value("${marvel.api.gateway.url}") String marvelApiGatewayUrl,
			final @Value("${marvel.api.key.private}") String marvelApiKeyPrivate,
			final @Value("${marvel.api.key.public}") String marvelApiKeyPublic) {
		this.webUtil = webUtil;
		this.caffeineCacheManager = caffeineCacheManager;
		this.cacheService = cacheService;
		this.marvelApiGatewayUrl = marvelApiGatewayUrl;
		this.marvelApiKeyPrivate = marvelApiKeyPrivate;
		this.marvelApiKeyPublic = marvelApiKeyPublic;
	}

	/**
	 * calls the CHARACTERS_GET_V1 API with the given params
	 * 
	 * @param query
	 * @param timestamp
	 * @return
	 * @throws Exception
	 */
	public GetCharactersResponseDTO fetchCharactersV1(final GetCharacterQuery query, final long timestamp)
			throws Exception {
		if (null == query) {
			throw new IllegalArgumentException("query cannot be null");
		}
		try {
			final String apiHash = HashingUtil.createHashString(timestamp + marvelApiKeyPrivate + marvelApiKeyPublic);

			final ResponseEntity<GetCharactersResponseDTO> responseEntity = webUtil.exchange(marvelApiGatewayUrl,
					Endpoint.CHARACTERS_GET_V1.getPath(), null, marvelApiKeyPublic, apiHash, timestamp,
					query.toParamsMap(), null, HttpMethod.GET,
					new ParameterizedTypeReference<GetCharactersResponseDTO>() {
					});
			if (!responseEntity.getStatusCode().is2xxSuccessful()) {
				throw new Exception(
						String.format("received unsuccessful status code: %s when getting characters with query",
								responseEntity.getStatusCode(), query.toString()));
			}
			final GetCharactersResponseDTO response = responseEntity.getBody();
			if (null == response) {
				return null;
			}
			log.info("received response: {}", response);
			return response;
		} catch (Exception e) {
			throw new Exception(String.format("Error occured when getting characters with query: %s error: %s", query,
					e.getLocalizedMessage()), e);
		}
	}

	/**
	 * get the characters from the cache if its available, or calls the
	 * CHARACTERS_GET_V1 api to fetch and store it in cache
	 * 
	 * @param query
	 * @param fetch
	 * @return
	 * @throws Exception
	 */
	public GetCharactersResponseDTO getCharactersV1(final GetCharacterQuery query, final boolean fetch)
			throws Exception {
		if (null == query) {
			throw new IllegalArgumentException("query cannot be null");
		}
		final GetCharactersResponseDTO characters;
		try {
			final String cacheKey = String.valueOf(query.getHash());
			if (fetch)
				cacheService.evictCharacters(cacheKey);

			final Cache cache = Objects
					.requireNonNull(caffeineCacheManager.getCache(CacheSetting.CHARACTERS.getCacheName()));
			final var cacheResult = cache.get(cacheKey, () -> fetchCharactersV1(query, System.currentTimeMillis()));
			characters = Optional.ofNullable(cacheResult).orElseThrow();
		} catch (Exception e) {
			throw e;
		}
		return characters;
	}
}
