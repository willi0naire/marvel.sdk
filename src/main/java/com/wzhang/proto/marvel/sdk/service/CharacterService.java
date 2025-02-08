package com.wzhang.proto.marvel.sdk.service;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wzhang.proto.marvel.sdk.dto.GetCharacterQuery;
import com.wzhang.proto.marvel.sdk.dto.GetCharactersResponseDTO;
import com.wzhang.proto.marvel.sdk.enumeration.Endpoint;
import com.wzhang.proto.marvel.sdk.util.HashingUtil;
import com.wzhang.proto.marvel.sdk.util.StringUtil;
import com.wzhang.proto.marvel.sdk.util.WebUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * service to deal with character related operations
 */
@Slf4j
public class CharacterService {
	private final String marvelApiGatewayUrl;
	private final String marvelApiKeyPrivate;
	private final String marvelApiKeyPublic;
	private final CacheService cacheService = CacheService.getInstance();

	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;

	public CharacterService(final String marvelApiGatewayUrl, final String marvelApiKeyPrivate,
			final String marvelApiKeyPublic) {
		httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30L)).build();
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
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
	private GetCharactersResponseDTO fetchCharactersV1(final GetCharacterQuery query, final long timestamp)
			throws Exception {
		if (null == query) {
			throw new IllegalArgumentException("query cannot be null");
		}
		try {
			final String apiHash = HashingUtil.createHashString(timestamp + marvelApiKeyPrivate + marvelApiKeyPublic);

			final Map<String, String> params = query.toParamsMap();
			if (StringUtil.isNotEmpty(marvelApiKeyPublic)) {
				params.put("apikey", marvelApiKeyPublic);
			}
			if (StringUtil.isNotEmpty(apiHash)) {
				params.put("hash", apiHash);
			}
			params.put("ts", String.valueOf(timestamp));

			var httpReq = WebUtil.constructRequest(marvelApiGatewayUrl, Endpoint.CHARACTERS_GET_V1, params,
					Map.of(WebUtil.HEADER_CONTENT_TYPE, WebUtil.MEDIA_TYPE_APPLICATION_JSON), WebUtil.METHOD_GET, null);

			var response = httpClient.send(httpReq, HttpResponse.BodyHandlers.ofString());
			if (!WebUtil.isSuccessStatusCode(response)) {
				throw new Exception(
						String.format("received unsuccessful status code: %s when getting characters with query",
								response.statusCode(), query.toString()));
			}
			if (StringUtil.isEmpty(response.body())) {
				throw new IllegalStateException("body is null or empty");
			}
			log.info("received response: {}", response);
			return objectMapper.readValue(response.body(), new TypeReference<GetCharactersResponseDTO>() {
			});
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
			var result = cacheService.getCharacters(cacheKey);
			if (null == result)
				result = cacheService.setCharacters(cacheKey, fetchCharactersV1(query, System.currentTimeMillis()));
			characters = Optional.ofNullable(result).orElseThrow();
		} catch (Exception e) {
			throw e;
		}
		return characters;
	}
}
