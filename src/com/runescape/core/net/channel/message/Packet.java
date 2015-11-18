package com.runescape.core.net.channel.message;

import java.nio.ByteBuffer;

/**
 * Represents a single packet.
 * @author SeVen
 */
public final class Packet {
	
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

	/**
	 * The underlying buffer backing of the packet.
	 */
	private final ByteBuffer buffer;

	/**
	 * The opcode and length of the incoming packet.
	 */
	private final int opcode, length;

	/**
	 * The default class constructor.
	 * 
	 * @param buffer The backing.
	 * 
	 * @param opcode The opcode.
	 * 
	 * @param length The length.
	 */
	public Packet(ByteBuffer buffer, int opcode, int length) {
		this.buffer = buffer;
		this.opcode = opcode;
		this.length = length;
	}

	/**
	 * Returns the underlying buffer backing.
	 * 
	 * @return The returned backing.
	 */
	public ByteBuffer getBuffer() {
		return buffer;
	}

	/**
	 * Returns the numerical opcode.
	 * 
	 * @return The returned opcode.
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * Returns the numerical length.
	 * 
	 * @return The returned length.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Reads a byte with a negated value.
	 * 
	 * @return The byte that was read.
	 */
	public byte readNegatedByte() {
		return (byte) (-getBuffer().get());
	}

	/**
	 * Reads a signed subtrahend.
	 * 
	 * @return The read subtrahend.
	 */
	public int readSignedSubtrahend() {
		return 128 - getBuffer().get();
	}

	/**
	 * Reads a short with an additional value inscribed.
	 * 
	 * @return The short that was read.
	 */
	public short readAdditionalShort() {
		int read = ((getBuffer().get() & 0xFF) << 8) | (getBuffer().get() - 128 & 0xFF);
		if (read > 32767) {
			read -= 0x10000;
		}
		return (short) read;
	}

	/**
	 * Reads a series of bytes from the underlying buffer.
	 * 
	 * @param amount The amount of bytes to be read.
	 * 
	 * @return The bytes that where read.
	 */
	public byte[] readBytes(int amount) {
		byte[] data = new byte[amount];
		for (int i = 0; i < amount; i++) {
			data[i] = getBuffer().get();
		}
		return data;
	}

	/**
	 * Reads a little endian short with an inscribed addition.
	 * 
	 * @return The little endian short that was read.
	 */
	public int readLittleEndianShortAddition() {
		int read = (getBuffer().get() - 128 & 0xFF) | ((getBuffer().get() & 0xFF) << 8);
		if (read > 32767) {
			read -= 0x10000;
		}
		return (short) read;
	}

	/**
	 * Reads a series of bytes with an addition of 128 to each.
	 * 
	 * @param amount The amount of bytes to be read.
	 * 
	 * @return The bytes that where read.
	 */
	public byte[] readBytesAddition(int amount) {
		byte[] bytes = new byte[amount];
		for (int i = 0; i < amount; i++) {
			bytes[i] = (byte) (getBuffer().get() + 128);
		}
		return bytes;
	}

	/**
	 * Reads a little endian short with no modifiers.
	 * 
	 * @return The little endian short that was read.
	 */
	public int readLittleEndianShort() {
		int read = (getBuffer().get() & 0xFF) | ((getBuffer().get() & 0xFF) << 8);
		if (read > 32767) {
			read -= 0x10000;
		}
		return (short) read;
	}
}