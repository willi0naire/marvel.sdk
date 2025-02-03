package com.wzhang.proto.marvel.sdk.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.wzhang.proto.marvel.sdk.enumeration.CacheSetting;

/**
 * cache configuration
 */
@Configuration
@EnableCaching
public class CaffineConfig {

	public static final String CAFFINE_CACHE_MANAGER = "caffineCacheManager";

	/**
	 * configures the cache manager
	 * 
	 * @param caffeine
	 * @return
	 */
	@Bean(CAFFINE_CACHE_MANAGER)
	public CaffeineCacheManager cacheManager(Caffeine<Object, Object> caffeine) {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.setCaffeine(caffeine);
		return caffeineCacheManager;
	}

	/**
	 * configures the cache bean
	 * 
	 * @return
	 */
	@Bean
	public Caffeine<Object, Object> caffeineConfig() {
		return Caffeine.newBuilder()
				.expireAfterWrite(CacheSetting.CHARACTERS.getTTL(), CacheSetting.CHARACTERS.getTimeUnit())
				.maximumSize(2000).recordStats();
	}

}
