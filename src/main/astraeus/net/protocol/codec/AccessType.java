package main.astraeus.net.protocol.codec;

public enum AccessType {

	/**
	 * Messages are being packed into the buffer in the form of bytes.
	 */
	BYTE_ACCESS,

	/**
	 * Messages are being packed into the buffer in the form of bits.
	 */
	BIT_ACCESS
}