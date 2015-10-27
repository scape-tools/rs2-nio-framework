package com.runescape.core.net.channel.message;

import java.nio.ByteBuffer;
import java.util.stream.IntStream;

import com.runescape.core.net.ByteAccess;
import com.runescape.core.net.ByteOrder;
import com.runescape.core.net.ByteValue;
import com.runescape.core.net.channel.message.Packet.PacketHeader;
import com.runescape.core.net.channel.protocol.ProtocolConstants;

/**
 * Functions as a write-only {@link ByteBuffer} for building outgoing messages.
 * 
 * @author SeVen
 */
public final class PacketBuilder {

	/**
	 * The internal buffer.
	 */
	private ByteBuffer builder;

	/**
	 * The message's length, indicated by the buffer's current position.
	 */
	private int length = 0;

	/**
	 * The current position of the bit-wise operation.
	 */
	private int position = 0;

	/**
	 * The id of the packet being created.
	 */
	private int opcode;
	
	/**
	 * The header or also known as the type of packet being sent.
	 */
	private PacketHeader header;
	
	/**
	 * Constructs a new {@link PacketBuilder} with no opcode and
	 * a Standard PacketHeader.
	 */
	public PacketBuilder() {
		this(-1);
	}
	
	/**
	 * Constructs a new {@link PacketBuilder} with a standard Packet Header.
	 * 
	 * @param opcode
	 * 		The id of the packet.
	 */
	public PacketBuilder(int opcode) {
		this(opcode, PacketHeader.STANDARD);
	}
	
	/**
	 * Constructs a new {@link PacketBuilder}
	 * 
	 * @param opcode
	 * 		The id of the packet.
	 * @param header
	 * 		The header type of the packet.
	 */
	public PacketBuilder(int opcode, PacketHeader header) {
		this.opcode = opcode;
		this.header = header;
	}
	
	/**
	 * Allocates a new byte buffer. 
	 */
	public void allocate(int amount) {
		this.builder  = ByteBuffer.allocate(amount);
	}
	
	/**
	 * Gets the opcode of this packet.
	 * 
	 * @return The id of this packet.
	 */
	public int getOpcode() {
		return opcode;
	}
	
	/**
	 * Gets the header for this packet, or also known as the type of packet being created.
	 * 
	 * @return The header of the packet being created.
	 */
	public PacketHeader getHeader() {
		return header;
	}
	
	/**
	 * Places a single byte value into the internal buffer.
	 * 
	 * @param value
	 * 		The value of this byte.
	 * 
	 * @param modification
	 * 		The manipulation of this byte value.
	 */
	public void put(long value, ByteValue modification) {
		switch (modification) {

		case ADDITIONAL:
			this.getInternal().put((byte) (value + 128));
			break;

		case NEGATED:
			this.getInternal().put((byte) -value);
			break;

		case STANDARD:
			this.getInternal().put((byte) value);
			break;

		case SUBTRACTED:
			this.getInternal().put((byte) (128 - value));
			break;
		}
	}
	
	/**
	 * Places a single integer value into the internal buffer.
	 * 
	 * @param value
	 * 		The value of this integer.
	 * 
	 * @param modification
	 * 		The manipulation of this integer value.
	 */
	public void putInt(long value, ByteValue modification) {
		switch (modification) {
		
		case ADDITIONAL:
			this.getInternal().putInt((int) (value + 128));
			break;

		case NEGATED:
			this.getInternal().putInt((int) -value);
			break;

		case STANDARD:
			this.getInternal().putInt((int) value);
			break;

		case SUBTRACTED:
			this.getInternal().putInt((int) (128 - value));
			break;
		}
	}
	
	/**
	 * Places a single long value into the internal buffer.
	 * 
	 * @param value
	 * 		The value of this long.
	 * 
	 * @param modification
	 * 		The manipulation of this long value.
	 */
	public void putLong(long value, ByteValue modification) {
		switch (modification) {

		case ADDITIONAL:
			this.getInternal().putLong(value + 128);
			break;

		case NEGATED:
			this.getInternal().putLong(-value);
			break;

		case STANDARD:
			this.getInternal().putLong(value);
			break;

		case SUBTRACTED:
			this.getInternal().putLong(128 - value);
			break;
		}
	}
	
	/**
	 * Places a single short value into the internal buffer that has
	 * a Standard {@link ByteValue}, and Big Byte Order {@link ByteOrder}
	 * 
	 * @param value
	 * 		The value of this short.
	 */
	public void putShort(int value) {
		putShort(value, ByteValue.STANDARD, ByteOrder.BIG_BYTE_ORDER);		
	}
	
	/**
	 * Places a single short into the internal buffer.
	 * 
	 * @param value
	 * 			The value of this short.
	 * 
	 * @param modification
	 * 			The manipulation of this short value.
	 */
	public void putShort(long value, ByteValue modification) {
		switch (modification) {

		case ADDITIONAL:
			this.getInternal().putShort((short) (value + 128));
			break;

		case NEGATED:
			this.getInternal().putShort((short) -value);
			break;

		case STANDARD:
			this.getInternal().putShort((short) value);
			break;

		case SUBTRACTED:
			this.getInternal().putShort((short) (128 - value));
			break;
		}
	}

	/**
	 * Places a single short into the internal buffer, which offers
	 * order specification.
	 * 
	 * @param value
	 * 		The value of this short.
	 * 
	 * @param modification
	 * 		The manipulation of this byte value.
	 * 
	 * @param order
	 * 		The order to be placed into the internal buffer.
	 */
	public final void putShort(int value, ByteValue modification, ByteOrder order) {
		switch (order) {
		case BIG_BYTE_ORDER:
			putByte(value >> 8, ByteValue.STANDARD);
			putByte(value, modification);
			break;

		case LITTLE_BYTE_ORDER:
			putByte(value, modification);
			putByte(value >> 8, ByteValue.STANDARD);
			break;

		default:
			throw new IllegalArgumentException("Invalid ordering definition.");
		}
	}
	
	/**
	 * Places bit values into the internal buffer.
	 * 
	 * @param value
	 * 		The value of these bits.
	 * 
	 * @param amount
	 * 		The amount
	 * 
	 * @param modification
	 * 		The manipulation of the byte values.
	 */
	public void putBits(long value, int amount, ByteValue modification) {	

		int bytePosition = this.getPosition() >> 3;
		int bitOffset = 8 - (this.getPosition() & 7);

		this.setPosition(this.getPosition() + amount);

		int requiredSpace = bytePosition - this.getInternal().position() + 1;
		requiredSpace += (amount + 7) / 8;

		if (this.getInternal().remaining() < requiredSpace) {

			ByteBuffer old = this.getInternal();
			this.setInternal(ByteBuffer.allocate(old.capacity() + requiredSpace));
			old.flip();
			this.getInternal().put(old);
		}
		for (; amount > bitOffset; bitOffset = 8) {

			byte temporary = this.getInternal().get(bytePosition);
			temporary &= ~ProtocolConstants.BIT_MASKS[bitOffset];
			temporary |= (value >> (amount - bitOffset)) & ProtocolConstants.BIT_MASKS[bitOffset];
			this.getInternal().put(bytePosition++, temporary);
			amount -= bitOffset;
		}
		if (amount == bitOffset) {

			byte temporary = this.getInternal().get(bytePosition);
			temporary &= ~ProtocolConstants.BIT_MASKS[bitOffset];
			temporary |= value & ProtocolConstants.BIT_MASKS[bitOffset];
			this.getInternal().put(bytePosition, temporary);
		} else {

			byte temporary = this.getInternal().get(bytePosition);
			temporary &= ~(ProtocolConstants.BIT_MASKS[amount] << (bitOffset - amount));
			temporary |= (value & ProtocolConstants.BIT_MASKS[amount]) << (bitOffset - amount);
			this.getInternal().put(bytePosition, temporary);
		}
	}

	/**
	 * Writes a series of bytes derived from the internal buffer.
	 * 
	 * @param source The internal buffer.
	 */
	public final void putBytes(ByteBuffer source) {		
		IntStream.range(0, source.position()).forEach(index -> {
			putByte(source.get(index), ByteValue.STANDARD);
		});
	}

	/**
	 * Places a single integer into the internal buffer.
	 * 
	 * @param value The value of the integer.
	 * 
	 * @param definition The definition of any transformations performed on the integer.
	 */
	public final void putInt(int value, ByteValue definition) {
		putInt(value, definition);
	}

	/**
	 * Places a single byte into the internal buffer.
	 * 
	 * @param value The value of the byte.
	 * 
	 * @param definition The definition of any transformations performed on the byte.
	 */
	public final void putByte(int value, ByteValue definition) {
		put(value, definition);
	}

	/**
	 * Places a set of bits into the internal buffer.
	 * 
	 * @param value The bit's value.
	 * 
	 * @param amount The amount of bits.
	 */
	public final void putBits(final int amount, int value) {
		putBits(value, amount, ByteValue.STANDARD);
	}

	/**
	 * Switches between the two different {@link ByteAccess} states.
	 * 
	 * @param access The access type modification.
	 */
	public final void setAccessType(ByteAccess access) {
		switch (access) {

		case BIT_ACCESS:
			setPosition(builder.position() * 8);
			break;

		case BYTE_ACCESS:
			builder.position((getPosition() + 7) / 8);
			break;
		}
	}

	/**
	 * Returns an instance of the internal buffer.
	 * 
	 * @return The returned instance.
	 */
	public final ByteBuffer getInternal() {
		return builder;
	}

	/**
	 * Returns the message's length.
	 * 
	 * @return The returned length.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Modifies the message's length.
	 * 
	 * @param length The new modification.
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Returns the current position of the bit-wise operation.
	 * 
	 * @return The returned position.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Modifies the current position of the bit-wise operation.
	 * 
	 * @param position The new modification.
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Modifies the instance of the internal buffer.
	 * 
	 * @param buffer The new modification.
	 */
	public void setInternal(ByteBuffer buffer) {
		this.builder = buffer;
	}
}