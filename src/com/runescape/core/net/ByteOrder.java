package com.runescape.core.net;

public enum ByteOrder {

	/**
	 * Stores the least significant byte in the smallest memory address.
	 */
	LITTLE_BYTE_ORDER,

	/**
	 * Stores the most significant byte in the smallest memory address.
	 */
	BIG_BYTE_ORDER,

	/**
	 * Stores the most significant part in the second to last byte.
	 */
	MIDDLE_BYTE_ORDER,

	/**
	 * Stores the most significant part in the second byte.
	 */
	INVERSE_MIDDLE_BYTE_ORDER;
}