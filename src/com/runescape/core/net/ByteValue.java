package com.runescape.core.net;

/**
 * Represent the Runescape's custom value types.
 * 
 * @author SeVen
 */
public enum ByteValue {
	/**
	 * No modifications
	 */
    STANDARD,
    /**
     * Adds 128 to the value.
     */
    ADDITION,
    /**
     * Invert the value.
     */
    INVERSION,
    /**
     * Subtract the value from 128.
     */
    SUBTRACTION
}