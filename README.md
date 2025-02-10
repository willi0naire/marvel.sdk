# Marvel SDK for Java

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.willi0naire/marvel.sdk.svg)](https://search.maven.org/artifact/com.github.willi0naire/marvel.sdk)
[![Build Status](https://travis-ci.org/willi0naire/marvel.sdk.svg?branch=master)](https://travis-ci.org/willi0naire/marvel.sdk)
[![Coverage Status](https://coveralls.io/repos/github/willi0naire/marvel.sdk/badge.svg?branch=master)](https://coveralls.io/github/willi0naire/marvel.sdk?branch=master)

A Java SDK for interacting with the Marvel Comics API. This SDK simplifies the process of making requests to the Marvel API and handling responses in Java applications.

## Installation

Add the Marvel SDK to your project using Maven or Gradle.

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>com.wzhang.proto</groupId>
  <artifactId>marvel.sdk</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle
Add the following dependency to your build.gradle:
```groovy
implementation 'com.wzhang.proto:marvel.sdk:1.0.0'
```

### Usage
To use the Marvel SDK, you need to obtain an API key and a private key from the [Marvel Developer Portal](https://developer.marvel.com/).
```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wzhang.proto.marvel.sdk.dto.GetCharacterQuery;
import com.wzhang.proto.marvel.sdk.service.CacheService;
import com.wzhang.proto.marvel.sdk.service.CharacterService;

@Service
public class TestService {
	private final CharacterService characterService;
	private final CacheService cacheService;
	private final String marvelApiGatewayUrl;
	private final String marvelApiKeyPrivate;
	private final String marvelApiKeyPublic;

	public TestService(final @Value("${marvel.api.gateway.url}") String marvelApiGatewayUrl,
			final @Value("${marvel.api.key.private}") String marvelApiKeyPrivate,
			final @Value("${marvel.api.key.public}") String marvelApiKeyPublic) {
		this.marvelApiGatewayUrl = marvelApiGatewayUrl;
		this.marvelApiKeyPrivate = marvelApiKeyPrivate;
		this.marvelApiKeyPublic = marvelApiKeyPublic;
		characterService = new CharacterService(this.marvelApiGatewayUrl, this.marvelApiKeyPrivate,
				this.marvelApiKeyPublic);
		cacheService = CacheService.getInstance();
	}
```

### Fetching Characters

You can search for a list of characters
```java
@Service
public class TestService {
...
	public void test() throws Exception {
		characterService.getCharactersV1(GetCharacterQuery.builder().nameStartsWith("s").build(), false); // miss
	}
}
```

You can get the stats on cache
```java
@Service
public class TestService {
...
	public void test() throws Exception {
		cacheService.getStats();
	}
}
```
