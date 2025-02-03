package com.wzhang.proto.marvel.sdk.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * service to deal with cache related operations
 */
@Slf4j
@Service
public class CacheService {

	private static final String CHARACTERS = "characters";

	/**
	 * evicts the characters cache by key
	 * 
	 * @param hashKey
	 */
	@CacheEvict(value = CHARACTERS, key = "#p0")
	public void evictCharacters(final String hashKey) {
		log.debug("evicted cache for: {}", hashKey);
	}
}
