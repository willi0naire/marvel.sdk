package com.wzhang.proto.marvel.sdk.enumeration;

/**
 * list of fields that can be used to sort with the character v1 api
 */
public enum GetCharactersSortField {
	NAME("name"), MODIFIED_SINCE("modifiedSince"),

	;

	private String fieldName;

	GetCharactersSortField(final String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return this.fieldName;
	}
}
