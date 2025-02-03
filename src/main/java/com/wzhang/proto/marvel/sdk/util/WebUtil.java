package com.wzhang.proto.marvel.sdk.util;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.annotation.Nonnull;

/**
 * helper class for REST client related operations
 *
 */
@Component
public class WebUtil {

	private final RestTemplate restTemplate;

	public WebUtil(final @Autowired RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * sends a restful request with the different input variables and returns a list
	 * of objects in the given responseType
	 * 
	 * @param <T>
	 * @param <R>
	 * @param url
	 * @param path
	 * @param body
	 * @param apiKey
	 * @param hash
	 * @param timestamp
	 * @param params
	 * @param urlParams
	 * @param method
	 * @param responseType
	 * @return
	 * @throws RestClientException
	 */
	public <T, R> ResponseEntity<T> exchange(@Nonnull String url, String path, R body, String apiKey, String hash,
			Long timestamp, MultiValueMap<String, String> params, Map<String, String> urlParams,
			@Nonnull HttpMethod method, ParameterizedTypeReference<T> responseType) throws RestClientException {
		return restTemplate.exchange(
				getRequestEntity(url, path, body, apiKey, hash, timestamp, params, urlParams, method), responseType);
	}

	/**
	 * creates {@link RequestEntity} with the given parameters
	 * 
	 * @param <T>
	 * @param url
	 * @param path
	 * @param body
	 * @param apiKey
	 * @param hash
	 * @param timestamp
	 * @param params
	 * @param urlParams
	 * @param method
	 * @return
	 */
	private <T> RequestEntity<T> getRequestEntity(@Nonnull String url, String path, T body, String apiKey, String hash,
			Long timestamp, MultiValueMap<String, String> params, Map<String, String> urlParams,
			@Nonnull HttpMethod method) {
		if (StringUtil.isNotEmpty(apiKey)) {
			params.add("apikey", apiKey);
		}
		if (StringUtil.isNotEmpty(hash)) {
			params.add("hash", hash);
		}
		if (null != timestamp) {
			params.add("ts", String.valueOf(timestamp));
		}
		final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).path(path).queryParams(params);
		final URI uri = null != urlParams && !urlParams.isEmpty() ? builder.buildAndExpand(urlParams).toUri()
				: builder.build().toUri();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return new RequestEntity<>(body, headers, method, uri);
	}
}
