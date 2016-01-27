package main.astraeus.core.net.protocol.codec;

/**
 * Represent the Runescape's custom value types.
 * 
 * @author SeVen
 */
public enum ByteModification {
	/**
	 * No modifications
	 */
    STANDARD,
    /**
     * Adds 128 to the value.
     */
    ADDITION,
    /**
     * Places a negative sign on the value.
     */
    NEGATION,
    /**
     * Subtract the value from 128.
     */
    SUBTRACTION
}