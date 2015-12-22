package main.astraeus.core.net.channel.protocol.codec.game;

public enum ByteAccess {

	/**
	 * Messages are being packed into the buffer in the form of bytes.
	 */
	BYTE_ACCESS,

	/**
	 * Messages are being packed into the buffer in the form of bits.
	 */
	BIT_ACCESS
}