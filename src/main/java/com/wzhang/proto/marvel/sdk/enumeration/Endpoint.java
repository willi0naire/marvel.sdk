package com.wzhang.proto.marvel.sdk.enumeration;

/**
 * enum used to define different API paths
 */
public enum Endpoint {

	CHARACTERS_GET_V1("v1/public/characters"),

	;

	private final String path;

	Endpoint(final String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

}
