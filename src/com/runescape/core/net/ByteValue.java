package com.runescape.core.net;

public enum ByteValue {

	/**
	 * No value modifications are performed.
	 */
	STANDARD,

	/**
	 * The value of the byte is raised by 128.
	 */
	ADDITIONAL,

	/**
	 * The value of the byte is turned negative.
	 */
	NEGATED,

	/**
	 * The value of the byte is subtracted from 128.
	 */
	SUBTRACTED;
}