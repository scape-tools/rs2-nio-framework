package main.astraeus.core.net.packet;

import java.nio.ByteBuffer;
import java.util.stream.IntStream;

import main.astraeus.core.net.protocol.ProtocolConstants;
import main.astraeus.core.net.protocol.codec.ByteAccess;
import main.astraeus.core.net.protocol.codec.ByteOrder;
import main.astraeus.core.net.protocol.codec.ByteValue;

/**
 * Functions as a write-only {@link ByteBuffer} for building outgoing messages.
 * 
 * @author SeVen
 */
public final class PacketBuilder {

	/**
	 * The internal buffer.
	 */
	private ByteBuffer buffer;

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
	 * Creates a new {@link PacketBuilder}. With a default opcode of {@code -1} and {@link PacketHeader} value of {@code EMPTY}.
	 */
	public PacketBuilder() {
		this(-1, PacketHeader.EMPTY);
	}
	
	/**
	 * Creates a new {@link PacketBuilder}. With a {@link PacketHeader} value of {@code STANDARD}.
	 * 
	 * @param opcode
	 * 		The opcode for this packet.
	 */
	public PacketBuilder(int opcode) {
		this(opcode, PacketHeader.STANDARD);
	}
	

	/**
	 * Creates a new {@link PacketBuilder}.
	 * 
	 * @param opcode
	 * 		The opcode for this packet.
	 * 
	 * @param header
	 * 		The header for this packet.
	 */
	public PacketBuilder(int opcode, PacketHeader header) {
		this.opcode = opcode;
		this.header = header;
	}
	
	/**
	 * Allocates memory for this buffer.
	 * 
	 * @param allocate
	 * 
	 * 		The memory to allocate.
	 * 
	 * @return The builder.
	 */
	public PacketBuilder allocate(int allocate) {
		this.buffer = ByteBuffer.allocate(allocate);
		return this;
	}

	/**
	 * Creates a new {@link PacketBuilder}.
	 * 
	 * @param buffer
	 * 		The buffer being used. 		
	 */	
	public PacketBuilder(ByteBuffer buffer) {
		this.buffer = buffer;
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
	public PacketBuilder put(long value, ByteValue modification) {
		switch (modification) {

		case ADDITION:
			buffer.put((byte) (value + 128));
			break;

		case NEGATION:
			buffer.put((byte) -value);
			break;

		case STANDARD:
			buffer.put((byte) value);
			break;

		case SUBTRACTION:
			buffer.put((byte) (128 - value));
			break;
		}
		return this;
	}
	
	/**
	 * Places a single integer value into the internal buffer.
	 * 
	 * @param value
	 * 		The value of this integer.
	 * 
	 * @param byteValue
	 * 		The manipulation of this integer value.
	 */
	public PacketBuilder putInt(int value, ByteValue byteValue) {
		switch (byteValue) {
		
		case ADDITION:
			buffer.putInt(value + 128);
			break;

		case NEGATION:
			buffer.putInt(-value);
			break;

		case STANDARD:
			buffer.putInt(value);
			break;

		case SUBTRACTION:
			buffer.putInt(128 - value);
			break;
		}
		return this;
	}
	
	/**
	 * Places a single integer value into the internal buffer.
	 * 
	 * @param value
	 * 		The value of this integer.
	 * 
	 * @param order
	 * 		The order in which the bytes are written.
	 */
	public PacketBuilder putInt(int value, ByteOrder order) {
		putInt(value, ByteValue.STANDARD, order);
		return this;
	}
	
	/**
	 * Places a single integer value into the internal buffer.
	 * 
	 * @param value
	 * 		The value of this integer.
	 * 
	 * @param byteValue
	 * 		The manipulations of this integer value.
	 * 
	 * @param order
	 * 		The order in which the bytes are written.
	 */
	public PacketBuilder putInt(int value, ByteValue byteValue, ByteOrder order) {
		switch(order) {		
		case BIG:
            putByte(value >> 24);
            putByte(value >> 16);
            putByte(value >> 8);
            putByte(value, byteValue);
			break;
			
		case INVERSE:
            putByte(value >> 16);
            putByte(value >> 24);
            putByte(value, byteValue);
            putByte(value >> 8);
			break;
			
		case LITTLE:
            putByte(value, byteValue);
            putByte(value >> 8);
            putByte(value >> 16);
            putByte(value >> 24);
			break;
			
		case MIDDLE:
            putByte(value >> 8);
            putByte(value, byteValue);
            putByte(value >> 24);
            putByte(value >> 16);
			break;	
		}
		return this;
	}
	
	/**
	 * Places a single long value into the internal buffer which has
	 * a default {@link ByteValue} of {@code STANDARD}.
	 * 
	 * @param value
	 * 		The value of this long.
	 * 
	 * @param modification
	 * 		The manipulation of this long value.
	 */
	public PacketBuilder putLong(long value) {
		putLong(value, ByteValue.STANDARD);
		return this;
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
	public PacketBuilder putLong(long value, ByteValue modification) {
		switch (modification) {

		case ADDITION:
			buffer.putLong(value + 128);
			break;

		case NEGATION:
			buffer.putLong(-value);
			break;

		case STANDARD:
			buffer.putLong(value);
			break;

		case SUBTRACTION:
			buffer.putLong(128 - value);
			break;
		}
		return this;
	}
	
	/**
	 * Places a single short value into the internal buffer that has
	 * a Standard {@link ByteValue}, and Big Byte Order {@link ByteOrder}
	 * 
	 * @param value
	 * 		The value of this short.
	 */
	public PacketBuilder putShort(int value) {
		putShort(value, ByteValue.STANDARD, ByteOrder.BIG);
		return this;
	}
	
	/**
	 * Places a single short value into the internal buffer that has
	 * a Standard {@link ByteValue}.
	 */
	public PacketBuilder putShort(int value, ByteOrder order) {
		putShort(value, ByteValue.STANDARD, order);
		return this;
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
	public PacketBuilder putShort(long value, ByteValue modification) {
		switch (modification) {

		case ADDITION:
			buffer.putShort((short) (value + 128));
			break;

		case NEGATION:
			buffer.putShort((short) -value);
			break;

		case STANDARD:
			buffer.putShort((short) value);
			break;

		case SUBTRACTION:
			buffer.putShort((short) (128 - value));
			break;
		}
		return this;
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
	public final PacketBuilder putShort(int value, ByteValue modification, ByteOrder order) {
		switch (order) {
		case BIG:
			putByte(value >> 8, ByteValue.STANDARD);
			putByte(value, modification);
			break;

		case LITTLE:
			putByte(value, modification);
			putByte(value >> 8, ByteValue.STANDARD);
			break;

		default:
			throw new IllegalArgumentException("Invalid ordering definition.");
		}
		return this;
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
	public PacketBuilder putBits(long value, int amount, ByteValue modification) {	

		int bytePosition = this.getPosition() >> 3;
		int bitOffset = 8 - (this.getPosition() & 7);

		this.setPosition(this.getPosition() + amount);

		int requiredSpace = bytePosition - buffer.position() + 1;
		requiredSpace += (amount + 7) / 8;

		if (buffer.remaining() < requiredSpace) {

			ByteBuffer old = buffer;
			setBuffer(ByteBuffer.allocate(old.capacity() + requiredSpace));
			old.flip();
			buffer.put(old);
		}
		for (; amount > bitOffset; bitOffset = 8) {

			byte temporary = buffer.get(bytePosition);
			temporary &= ~ProtocolConstants.BIT_MASKS[bitOffset];
			temporary |= (value >> (amount - bitOffset)) & ProtocolConstants.BIT_MASKS[bitOffset];
			buffer.put(bytePosition++, temporary);
			amount -= bitOffset;
		}
		if (amount == bitOffset) {

			byte temporary = buffer.get(bytePosition);
			temporary &= ~ProtocolConstants.BIT_MASKS[bitOffset];
			temporary |= value & ProtocolConstants.BIT_MASKS[bitOffset];
			buffer.put(bytePosition, temporary);
		} else {

			byte temporary = buffer.get(bytePosition);
			temporary &= ~(ProtocolConstants.BIT_MASKS[amount] << (bitOffset - amount));
			temporary |= (value & ProtocolConstants.BIT_MASKS[amount]) << (bitOffset - amount);
			buffer.put(bytePosition, temporary);
		}
		return this;
	}
	
	/**
	 * Places a flag into the buffer.
	 * 
	 * @param flag
	 * 		The flag to place into the buffer.
	 */
	public PacketBuilder putBit(boolean flag) {
		putBits(1, flag ? 1 : 0);
		return this;
	}

	/**
	 * Writes a series of bytes derived from the internal buffer.
	 * 
	 * @param source The internal buffer.
	 */
	public final PacketBuilder putBytes(ByteBuffer source) {		
		IntStream.range(0, source.position()).forEach(index -> {
			putByte(source.get(index), ByteValue.STANDARD);
		});
		return this;
	}
	
	/**
	 * Places a single byte into the internal buffer.
	 * 
	 * @param value The value of the byte.
	 */
	public final PacketBuilder putByte(int value) {
		put(value, ByteValue.STANDARD);
		return this;
	}

	/**
	 * Places a single byte into the internal buffer.
	 * 
	 * @param value The value of the byte.
	 * 
	 * @param byteValue The definition of any transformations performed on the byte.
	 */
	public final PacketBuilder putByte(int value, ByteValue byteValue) {
		put(value, byteValue);
		return this;
	}

	/**
	 * Places a set of bits into the internal buffer.
	 * 
	 * @param value The bit's value.
	 * 
	 * @param amount The amount of bits.
	 */
	public final PacketBuilder putBits(final int amount, int value) {
		putBits(value, amount, ByteValue.STANDARD);
		return this;
	}

	/**
	 * Switches between the two different {@link ByteAccess} states.
	 * 
	 * @param access The access type modification.
	 */
	public final PacketBuilder setAccessType(ByteAccess access) {
		switch (access) {

		case BIT_ACCESS:
			setPosition(buffer.position() * 8);
			break;

		case BYTE_ACCESS:
			buffer.position((getPosition() + 7) / 8);
			break;
		}
		return this;
	}
	
	/**
	 * Places a series of bytes into a buffer.
	 * 
	 * @param string
	 * 		The string to place into the buffer.
	 */
	public final PacketBuilder putString(String string) {
		buffer.put(string.getBytes());
		buffer.put((byte) 10);
		return this;
	}
	
	/**
	 * Completes the variable short packet header.
	 */
	public final PacketBuilder endVariableShortPacketHeader() {
		buffer.putShort(getLength(), (short) (getPosition() - getLength() - 2));
		return this;
	}

	/**
	 * Returns an instance of the internal buffer.
	 * 
	 * @return The returned instance.
	 */
	public final ByteBuffer getBuffer() {
		return buffer;
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
	public void setBuffer(ByteBuffer buffer) {
		this.buffer = buffer;
	}
}