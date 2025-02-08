package com.wzhang.proto.marvel.sdk.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * hashing related helper methods
 */
public class HashingUtil {
	/**
	 * Prime number used for hashing algorithm
	 */
	private static final int PRIME_NUMBER = 31;

	/**
	 * hashes the given secret into byte array with MD5
	 * 
	 * @param secret
	 * @return
	 */
	public static byte[] createHashBytes(final String secret) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
		return md.digest(secret.getBytes());
	}

	/**
	 * hashes the given secret into string with MD5
	 * 
	 * @param secret
	 * @return
	 */
	public static String createHashString(final String secret) {
		return bytesToHex(createHashBytes(secret));
	}

	/**
	 * calculates the hash code of the given object
	 * 
	 * @param object
	 * @param key
	 * @return
	 */
	public static int getHashCode(final Object object, final String key) {
		if (null == object) {
			return PRIME_NUMBER;
		}
		final int objectNameHash = object.getClass().getName().hashCode();

		return PRIME_NUMBER * objectNameHash + (null == key ? object.hashCode() : key.hashCode());
	}

	/**
	 * converts byte array to string
	 * 
	 * @param bytes
	 * @return
	 */
	private static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
}
