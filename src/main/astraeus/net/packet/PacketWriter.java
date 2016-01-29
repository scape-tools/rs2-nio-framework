package main.astraeus.net.packet;

import java.nio.ByteBuffer;
import java.util.stream.IntStream;

import main.astraeus.net.protocol.ProtocolConstants;
import main.astraeus.net.protocol.codec.AccessType;
import main.astraeus.net.protocol.codec.ByteModification;
import main.astraeus.net.protocol.codec.ByteOrder;

/**
 * Functions as a write-only {@link ByteBuffer} for writing outgoing messages.
 * 
 * @author SeVen
 */
public final class PacketWriter {

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
       * Creates a new {@link PacketWriter}. With a default opcode of {@code -1} and
       * {@link PacketHeader} value of {@code EMPTY}.
       */
      public PacketWriter() {
            this(-1, PacketHeader.EMPTY);
      }

      /**
       * Creates a new {@link PacketWriter}. With a {@link PacketHeader} value of {@code STANDARD}.
       * 
       * @param opcode The opcode for this packet.
       */
      public PacketWriter(int opcode) {
            this(opcode, PacketHeader.FIXED);
      }


      /**
       * Creates a new {@link PacketWriter}.
       * 
       * @param opcode The opcode for this packet.
       * 
       * @param header The header for this packet.
       */
      public PacketWriter(int opcode, PacketHeader header) {
            this.opcode = opcode;
            this.header = header;
      }

      /**
       * Allocates memory for this buffer.
       * 
       * @param allocate
       * 
       *        The memory to allocate.
       * 
       * @return The builder.
       */
      public PacketWriter allocate(int allocate) {
            this.buffer = ByteBuffer.allocate(allocate);
            return this;
      }

      /**
       * Creates a new {@link PacketWriter}.
       * 
       * @param buffer The buffer being used.
       */
      public PacketWriter(ByteBuffer buffer) {
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
       * @param value The value of this byte.
       * 
       * @param modification The manipulation of this byte value.
       */
      public PacketWriter write(long value, ByteModification modification) {
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
       * Writes the bytes from the argued byte array into this buffer, in reverse.
       *
       * @param data the data to write to this buffer.
       */
      public PacketWriter writeBytesReverse(byte[] data) {
            for (int i = data.length - 1; i >= 0; i--) {
                  write(data[i]);
            }
            return this;
      }

      /**
       * Writes a value as a standard big-endian {@code int}.
       *
       * @param value the value to write.
       * @return an instance of this message builder.
       */
      public PacketWriter writeInt(int value) {
            writeInt(value, ByteModification.STANDARD, ByteOrder.BIG);
            return this;
      }

      /**
       * Places a single integer value into the internal buffer.
       * 
       * @param value The value of this integer.
       * 
       * @param byteValue The manipulation of this integer value.
       */
      public PacketWriter writeInt(int value, ByteModification byteValue) {
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
       * @param value The value of this integer.
       * 
       * @param order The order in which the bytes are written.
       */
      public PacketWriter writeInt(int value, ByteOrder order) {
            writeInt(value, ByteModification.STANDARD, order);
            return this;
      }

      /**
       * Places a single integer value into the internal buffer.
       * 
       * @param value The value of this integer.
       * 
       * @param byteValue The manipulations of this integer value.
       * 
       * @param order The order in which the bytes are written.
       */
      public PacketWriter writeInt(int value, ByteModification byteValue, ByteOrder order) {
            switch (order) {
                  case BIG:
                        write(value >> 24);
                        write(value >> 16);
                        write(value >> 8);
                        writeByte(value, byteValue);
                        break;

                  case INVERSE:
                        write(value >> 16);
                        write(value >> 24);
                        writeByte(value, byteValue);
                        write(value >> 8);
                        break;

                  case LITTLE:
                        writeByte(value, byteValue);
                        write(value >> 8);
                        write(value >> 16);
                        write(value >> 24);
                        break;

                  case MIDDLE:
                        write(value >> 8);
                        writeByte(value, byteValue);
                        write(value >> 24);
                        write(value >> 16);
                        break;
            }
            return this;
      }

      /**
       * Places a single long value into the internal buffer which has a default
       * {@link ByteModification} of {@code STANDARD}.
       * 
       * @param value The value of this long.
       * 
       * @param modification The manipulation of this long value.
       */
      public PacketWriter writeLong(long value) {
            writeLong(value, ByteModification.STANDARD);
            return this;
      }

      /**
       * Places a single long value into the internal buffer.
       * 
       * @param value The value of this long.
       * 
       * @param modification The manipulation of this long value.
       */
      public PacketWriter writeLong(long value, ByteModification modification) {
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
       * Places a single short value into the internal buffer that has a Standard
       * {@link ByteModification}, and Big Byte Order {@link ByteOrder}
       * 
       * @param value The value of this short.
       */
      public PacketWriter writeShort(int value) {
            writeShort(value, ByteModification.STANDARD, ByteOrder.BIG);
            return this;
      }

      /**
       * Places a single short value into the internal buffer that has a Standard
       * {@link ByteModification}.
       */
      public PacketWriter writeShort(int value, ByteOrder order) {
            writeShort(value, ByteModification.STANDARD, order);
            return this;
      }

      /**
       * Places a single short into the internal buffer.
       * 
       * @param value The value of this short.
       * 
       * @param modification The manipulation of this short value.
       */
      public PacketWriter writeShort(long value, ByteModification modification) {
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
       * Places a single short into the internal buffer, which offers order specification.
       * 
       * @param value The value of this short.
       * 
       * @param modification The manipulation of this byte value.
       * 
       * @param order The order to be placed into the internal buffer.
       */
      public final PacketWriter writeShort(int value, ByteModification modification,
                  ByteOrder order) {
            switch (order) {
                  case BIG:
                        writeByte(value >> 8, ByteModification.STANDARD);
                        writeByte(value, modification);
                        break;

                  case LITTLE:
                        writeByte(value, modification);
                        writeByte(value >> 8, ByteModification.STANDARD);
                        break;

                  default:
                        throw new IllegalArgumentException("Invalid ordering definition.");
            }
            return this;
      }

      /**
       * Places bit values into the internal buffer.
       * 
       * @param value The value of these bits.
       * 
       * @param amount The amount
       * 
       * @param modification The manipulation of the byte values.
       */
      public PacketWriter writeBits(long value, int amount, ByteModification modification) {

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
                  temporary |= (value >> (amount - bitOffset))
                              & ProtocolConstants.BIT_MASKS[bitOffset];
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
                  temporary |= (value & ProtocolConstants.BIT_MASKS[amount]) << (bitOffset
                              - amount);
                  buffer.put(bytePosition, temporary);
            }
            return this;
      }


      /**
       * Writes a series of bytes derived from the internal buffer.
       * 
       * @param source The internal buffer.
       */
      public final PacketWriter writeBytes(ByteBuffer source) {
            IntStream.range(0, source.position()).forEach(index -> {
                  writeByte(source.get(index), ByteModification.STANDARD);
            });
            return this;
      }

      /**
       * Places a single byte into the internal buffer.
       * 
       * @param value The value of the byte.
       */
      public final PacketWriter write(int value) {
            write(value, ByteModification.STANDARD);
            return this;
      }

      /**
       * Places a single byte into the internal buffer.
       * 
       * @param value The value of the byte.
       * 
       * @param byteValue The definition of any transformations performed on the byte.
       */
      public final PacketWriter writeByte(int value, ByteModification byteValue) {
            write(value, byteValue);
            return this;
      }

      /**
       * Places a flag into the buffer.
       * 
       * @param update The flag to place into the buffer.
       */
      public final PacketWriter writeBit(boolean update) {
            writeBits(1, update ? 1 : 0);
            return this;
      }

      /**
       * Places a set of bits into the internal buffer.
       * 
       * @param value The bit's value.
       * 
       * @param amount The amount of bits.
       */
      public final PacketWriter writeBits(final int amount, int value) {
            writeBits(value, amount, ByteModification.STANDARD);
            return this;
      }

      /**
       * Switches between the two different {@link ByteAccess} states.
       * 
       * @param access The access type modification.
       */
      public final PacketWriter setAccessType(AccessType access) {
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
       * @param string The string to place into the buffer.
       */
      public final PacketWriter writeString(String string) {
            buffer.put(string.getBytes());
            buffer.put((byte) 10);
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
