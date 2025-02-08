package com.wzhang.proto.marvel.sdk.util;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.wzhang.proto.marvel.sdk.enumeration.Endpoint;

/**
 * helper class for REST client related operations
 *
 */
public class WebUtil {
	public static final String METHOD_GET = "GET";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String MEDIA_TYPE_APPLICATION_JSON = "application/json";

	public static String toQueryString(final Map<String, String> paramsMap) {
		if (null == paramsMap || paramsMap.isEmpty())
			return "";
		return paramsMap.entrySet().stream()
				.map(p -> URLEncoder.encode(p.getKey(), StandardCharsets.UTF_8) + "="
						+ URLEncoder.encode(p.getValue(), StandardCharsets.UTF_8))
				.reduce((p1, p2) -> p1 + "&" + p2).orElse("");
	}

	public static HttpRequest constructRequest(final String host, final Endpoint endpoint,
			final Map<String, String> paramsMap, final Map<String, String> headers, final String method,
			final String body) throws Exception {
		if (StringUtil.isEmpty(host))
			throw new IllegalArgumentException("host is null or empty");
		if (null == endpoint)
			throw new IllegalArgumentException("endpoint is null");
		if (StringUtil.isEmpty(method))
			throw new IllegalArgumentException("method is null or empty");

		final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
				.uri(URI.create(String.format("%s/%s?%s", host, endpoint.getPath(), toQueryString(paramsMap))));
		requestBuilder.method(method,
				StringUtil.isNotEmpty(body) ? BodyPublishers.ofString(body) : BodyPublishers.noBody());
		if (null != headers && !headers.isEmpty()) {
			headers.entrySet().stream().forEach(e -> requestBuilder.header(e.getKey(), e.getValue()));
		}

		return requestBuilder.build();
	}

	public static <T> boolean isSuccessStatusCode(HttpResponse<T> httpResponse) {
		if (null == httpResponse)
			return false;
		return httpResponse.statusCode() >= 200 && httpResponse.statusCode() <= 299;
	}
}
