package com.astraeus.core.net.channel.packet;

/**
 * The type of packet, also the header of a packet.
 */
public enum PacketHeader {
	/**
	 * No packet header is to be placed on the message.
	 */
	EMPTY,

	/**
	 * A standard packet header.
	 */
	STANDARD,

	/**
	 * A header where the packet's length is written as a byte.
	 */
	VARIABLE_BYTE,

	/**
	 * A header where the packet's length is written as a short.
	 */
	VARIABLE_SHORT;
}
