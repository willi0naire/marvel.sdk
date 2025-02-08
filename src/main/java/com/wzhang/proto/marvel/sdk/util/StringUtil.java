package com.wzhang.proto.marvel.sdk.util;

/**
 * string related helper methods
 */
public class StringUtil {
	/**
	 * check if the given string is not null and not empty
	 * 
	 * @param inString
	 * @return
	 */
	public static boolean isNotEmpty(final String inString) {
		return null != inString && !inString.isBlank();
	}

	/**
	 * check if the given string is null or empty
	 * 
	 * @param inString
	 * @return
	 */
	public static boolean isEmpty(final String inString) {
		return null == inString || inString.isBlank();
	}
}
