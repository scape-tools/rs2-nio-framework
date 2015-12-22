package main.astraeus.core.net.channel.packet;

import java.nio.ByteBuffer;

/**
 * Represents a single incoming packet.
 * 
 * @author SeVen
 */
public final class IncomingPacket {

	/**
	 * The internal buffer.
	 */
	private final ByteBuffer buffer;

	/**
	 * The opcode of this packet.
	 */
	private final int opcode;
	
	/**
	 * The length of this packet.
	 */
	private final int length;

	/**
	 * Constructs a new {@link IncomingPacket}.
	 * 
	 * @param buffer
	 *		The internal buffer.
	 * 
	 * @param opcode
	 * 		The opcode of this packet.
	 * 
	 * @param length
	 * 		The length of this packet.
	 */
	public IncomingPacket(ByteBuffer buffer, int opcode, int length) {
		this.buffer = buffer;
		this.opcode = opcode;
		this.length = length;
	}

	/**
	 * Gets the internal buffer.
	 * 
	 * @return The internal buffer.
	 */
	public ByteBuffer getBuffer() {
		return buffer;
	}

	/**
	 * Gets the opcode.
	 * 
	 * @return The opcode.
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * Gets the length.
	 * 
	 * @return The length.
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
	 * Reads a little endian short with no modifications.
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