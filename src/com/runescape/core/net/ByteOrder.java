package com.runescape.core.net;

public enum ByteOrder {

	/**
	 * Stores the least significant byte in the smallest memory address.
	 */
	LITTLE,

	/**
	 * Stores the most significant byte in the smallest memory address.
	 */
	BIG,

	/**
	 * Stores the most significant part in the second to last byte.
	 */
	MIDDLE,

	/**
	 * Stores the most significant part in the second byte.
	 */
	INVERSE;
}