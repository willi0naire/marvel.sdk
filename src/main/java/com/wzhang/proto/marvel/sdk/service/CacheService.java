package com.wzhang.proto.marvel.sdk.service;

import com.wzhang.proto.marvel.sdk.cache.ExpirableCache;
import com.wzhang.proto.marvel.sdk.dto.GetCharactersResponseDTO;
import com.wzhang.proto.marvel.sdk.enumeration.CacheSetting;

import lombok.extern.slf4j.Slf4j;

/**
 * service to deal with cache related operations
 */
@Slf4j
public class CacheService {
	private static CacheService INSTANCE;
	private static ExpirableCache<String, GetCharactersResponseDTO> charactersCache;

	private CacheService() {
	};

	public static CacheService getInstance() {
		if (null == INSTANCE) {
			INSTANCE = new CacheService();
			charactersCache = new ExpirableCache<>(CacheSetting.CHARACTERS.getTTL(),
					CacheSetting.CHARACTERS.getTimeUnit());
		}
		return INSTANCE;
	}

	public GetCharactersResponseDTO setCharacters(final String hashKey, final GetCharactersResponseDTO characters) {
		final GetCharactersResponseDTO cached = charactersCache.putIfAbsent(hashKey, characters);
		if (null == cached)
			log.info("no cache found for key=" + hashKey);
		return null != cached ? cached : charactersCache.get(hashKey);
	}

	public GetCharactersResponseDTO getCharacters(final String hashKey) {
		return charactersCache.get(hashKey);
	}

	public boolean hasCharacters(final String hashKey) {
		return charactersCache.contains(hashKey);
	}

	/**
	 * evicts the characters cache by key
	 * 
	 * @param hashKey
	 */
	public void evictCharacters(final String hashKey) {
		final boolean evicted = charactersCache.remove(hashKey);
		log.info("evicted cache for key={} result={}", hashKey, evicted);
	}

	public void printHitMiss() {
		final long hit = charactersCache.getHit();
		final long miss = charactersCache.getMiss();
		final long total = hit + miss;
		log.info("total hit: {} | total miss: {} | hit ratio: {}% | miss ratio: {}%", hit, miss, (hit * 100.0f) / total,
				(miss * 100.0f) / total);
	}
}
