package com.wzhang.proto.marvel.sdk.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * enum to define the type of the story summary
 */
public enum StorySummaryType {
	COVER("cover"), INTERIOR("interior"),

	;

	private String typeStr;

	StorySummaryType(final String typeStr) {
		this.typeStr = typeStr;
	}

	@JsonValue
	public String getTypeStr() {
		return this.typeStr;
	}

	@JsonCreator
	public static StorySummaryType getType(final String typeStr) {
		for (StorySummaryType item : values()) {
			if (item.getTypeStr() == typeStr) {
				return item;
			}
		}
		return null;
	}
}
