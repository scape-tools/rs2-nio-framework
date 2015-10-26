package com.runescape.core.net.channel.message;

/**
 * Represents a single packet.
 * @author SeVen
 */
public class Packet {
	
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
	 * The id of the packet.
	 */
	private final int opcode;
	
	/**
	 * The type of packet. 
	 */
	private PacketHeader packetType;
	
	/**
	 * The buffer for getting the packet information.
	 */
	private PacketBuilder buffer;
	
	/**
	 * Constructs a new {@link Packet}
	 * 
	 * @param opcode
	 * 		The id of the packet.
	 * @param packetType
	 * 		The type of the packet.
	 * @param buffer
	 * 		The buffer used to recieve information.
	 */
	public Packet(int opcode, PacketHeader packetType, PacketBuilder buffer) {
		this.opcode = opcode;
		this.packetType = packetType;
		this.buffer = buffer;		
	}
	
	/**
	 *  Gets an unsigned byte
	 *  @return An unsigned byte.
	 */
	public byte getByte() {
		byte b = 0;
		try {
			b = buffer.getInternal().get();
		} catch(IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return b;
	}
	
	/**
	 * Gets a a byte of type A
	 * @return The unsigned byte - 128
	 */
	public byte getByteA() {
		return (byte) (getByte() -  128);
	}
	
	/**
	 * Gets a byte of type C
	 * @return a negative unsigned byte.
	 */
	public byte getByteC() {
		return (byte) (-getByte());
	}
	
	/**
	 * Gets a byte of type S
	 * @return 128 - The unsigned byte.
	 */
	public byte getByteS() {
		return (byte) (128 - getByte());
	}
	
	/**
	 * Gets an unsigned byte of type S
	 * @return The unsigned byte.
	 */
	public int getUnsignedByteS() {
		return getByteS() & 0xff;
	}
	
	/**
	 * Gets a byte array
	 * @return The byte array.
	 */
	public Packet getBytes(byte[] bytes) {
		this.buffer.getInternal().get(bytes);
		return this;
	}
	
	/**
	 * Gets number of bytes.
	 * @param amount
	 * 		The amount of bytes to get.
	 * @return The value of the bytes.
	 */
	public byte[] getBytes(int amount) {
		byte[] bytes = new byte[amount];		
		for (int i = 0; i < amount; i++) {
			bytes[i] = getByte();
		}		
		return bytes;
	}
	
	/**
	 * Gets an amount of bytes of type A
	 * @param amount	The amount of bytes of type A to get.
	 * @return 			The bytes array values.
	 */
	public byte[] getBytesA(int amount) {
		if (amount < 0)
			throw new NegativeArraySizeException("The byte array amount cannot have a negative value!");
		byte[] bytes = new byte[amount];
		for (int i = 0; i < amount; i++) {
			bytes[i] = (byte) (getByte() + 128);
		}
		return bytes;
	}

	/**
	 * Gets an amount of Reversed Bytes of type A	
	 * @param amount
	 * 			The amount of bytes to get.
	 * @return
	 * 		The amount of type A reversal bytes.
	 */
	public byte[] getReversedBytesA(int amount) {
		byte[] bytes = new byte[amount];
		int position = amount - 1;
		for (; position >= 0; position--) {
			bytes[position] = (byte) (getByte() + 128);
		}
		return bytes;
	}

	/**
	 * Gets an unsigned byte.
	 * 
	 * @return The unsigned byte value
	 */
	public int getUnsignedByte() {
		return buffer.getInternal().get(); //TODO wrong
	}

	/**
	 * Gets a short value.
	 * 
	 * @return	The short value.
	 */
	public short getShort() {
		return buffer.getInternal().getShort();
	}

	/**
	 * Gets a short of type A
	 * @return	The type A short.
	 */
	public short getShortA() {
		int value = ((getByte() & 0xFF) << 8) | (getByte() - 128 & 0xFF);
		return (short) (value > 32767 ? value - 0x10000 : value);
	}

	/**
	 * Gets a little-endian short.
	 * @return	The little-endian short value.
	 */
	public short getLEShort() {
		int value = (getByte() & 0xFF) | (getByte() & 0xFF) << 8;
		return (short) (value > 32767 ? value - 0x10000 : value);
	}

	/**
	 * Gets a little-endian short of type A
	 * @return	The little-endian short of type A.
	 */
	public short getLEShortA() {
		int value = (getByte() - 128 & 0xFF) | (getByte() & 0xFF) << 8;
		return (short) (value > 32767 ? value - 0x10000 : value);
	}

//	/**
//	 * Gets the unsigned short value from the packet.
//	 * @return	The unsigned short value.
//	 */
//	public int getUnsignedShort() {
//		return buffer.getUnsignedShort();
//	}

	/**
	 * Gets the unsigned short value packetType-A from the packet.
	 * @return	The unsigned short packetType-A value.
	 */
	public int getUnsignedShortA() {
		int value = 0;
		value |= getUnsignedByte() << 8;
		value |= (getByte() - 128) & 0xff;
		return value;
	}

	/**
	 * Gets an int value.
	 * @return	The int value.
	 */
	public int getInt() {
		return buffer.getInternal().getInt();
	}

	/**
	 * Gets a single int value.
	 * @return	The single int value.
	 */
	public int getSingleInt() {
		byte firstByte = getByte(), secondByte = getByte(), thirdByte = getByte(), fourthByte = getByte();
		return ((thirdByte << 24) & 0xFF) | ((fourthByte << 16) & 0xFF) | ((firstByte << 8) & 0xFF) | (secondByte & 0xFF);
	}

	/**
	 * Gets a double int value from the packet.
	 * @return	The double int value.
	 */
	public int getDoubleInt() {
		int firstByte = getByte() & 0xFF, secondByte = getByte() & 0xFF, thirdByte = getByte() & 0xFF, fourthByte = getByte() & 0xFF;
		return ((secondByte << 24) & 0xFF) | ((firstByte << 16) & 0xFF) | ((fourthByte << 8) & 0xFF) | (thirdByte & 0xFF);
	}

	/**
	 * Gets a triple int value from the packet.
	 * @return	The triple int value.
	 */
	public int getTripleInt() {
		return ((getByte() << 16) & 0xFF) | ((getByte() << 8) & 0xFF) | (getByte() & 0xFF);
	}

	/**
	 * Gets the long value from the packet.
	 * @return	The long value.
	 */
	public long getLong() {
		return buffer.getInternal().getLong();
	}

//	public byte[] getBytesReverse(int amount, ByteValue type) {
//		byte[] data = new byte[amount];
//		int dataPosition = 0;
//		for (int i = buffer.writerIndex() + amount - 1; i >= buffer.writerIndex(); i--) {
//			int value = buffer.getByte(i);
//			switch (type) {
//			case A:
//				value -= 128;
//				break;
//			case NEGATED:
//				value = -value;
//				break;
//			case SUBTRAHEND:
//				value = 128 - value;
//				break;
//			case STANDARD:
//				break;
//			}
//			data[dataPosition++] = (byte) value;
//		}
//		return data;
//	}
	
	/**
	 * Gets the string value.
	 * @return	The string value.
	 */
	public String getString() {
		StringBuilder builder = new StringBuilder();
		byte value;
		while (buffer.getInternal().isReadOnly() && (value = buffer.getInternal().get()) != 10) {
			builder.append((char) value);
		}
		return builder.toString();
	}

//	/**
//	 * Getss a smart value from the packet.
//	 * @return	The smart value.
//	 */
//	public int getSmart() {
//		return buffer.getByte(buffer.readerIndex()) < 128 ? getByte() & 0xFF : (getShort() & 0xFFFF) - 32768;
//	}

//	/**
//	 * Gets a signed smart value from the packet.
//	 * @return	The signed smart value.
//	 */
//	public int getSignedSmart() {
//		return buffer.getByte(buffer.getInternal().) < 128 ? (getByte() & 0xFF) - 64 : (getShort() & 0xFFFF) - 49152;
//	}

	/**
	 * @return the opcode
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * @return the packetType
	 */
	public PacketHeader getPacketType() {
		return packetType;
	}

	/**
	 * @return the buffer
	 */
	public PacketBuilder getBuffer() {
		return buffer;
	}
	
	/**
	 * @return The size of the buffer
	 */
	public int getSize() {
		return buffer.getInternal().remaining();
	}
	
	/**
	 * @return The length of the buffer.
	 */
	public int getLength() {
		return buffer.getLength();
	}
	
	@Override
	public String toString() {
		return "Packet - [opcode, size] : [" + getOpcode() + ", " + getSize() + "]";
	}
	
}
