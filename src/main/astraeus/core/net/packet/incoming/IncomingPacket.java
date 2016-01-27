package main.astraeus.core.net.packet.incoming;

import java.nio.ByteBuffer;

import main.astraeus.core.net.packet.PacketHeader;
import main.astraeus.core.net.protocol.codec.ByteModification;
import main.astraeus.core.net.protocol.codec.ByteOrder;

/**
 * Represents a single incoming packet.
 * 
 * @author SeVen
 */
public final class IncomingPacket {  

      /**
       * The opcode of this packet.
       */
      private final int opcode;
      
      /**
       * The header for this packet.
       */
      private final PacketHeader header;
      
      /**
       * The payload for this packet.
       */
      private final ByteBuffer payload;
      
      /**
       * The size of this packet.
       */
      private final int size;
      
      /**
       * The length of this packet.
       */
      private final int length;

      /**
       * Constructs a new {@link IncomingPacket}.
       * 
       * @param payload The internal buffer.
       * 
       * @param opcode The opcode of this packet.
       */
      public IncomingPacket(int opcode, PacketHeader header, ByteBuffer payload) {
            this.opcode = opcode;
            this.header = header;
            this.payload = payload;
            this.size = payload.remaining();
            this.length = payload.capacity();            
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
       * Gets the header for this packet.
       */
      public PacketHeader getHeader() {
            return header;
      }

      /**
       * Gets the payload for this packet.
       * 
       * @return The payload
       */
      public ByteBuffer getPayload() {
            return payload;
      }
      
      
      /**
       * Gets the size of this packet.
       */
      public int getSize() {
            return payload.remaining();
      }
      
      /**
       * Gets the length of this packet.
       */
      public int getLength() {
            return payload.capacity();
      }
      
      /**
       * Reads a byte with a negated value.
       * 
       * @return The byte that was read.
       */
      public byte readByteI() {
            return (byte) (-readByte());
      }
      
      public byte readByte() {
            return payload.get();
      }

      /**
       * Reads a signed subtrahend.
       * 
       * @return The read subtrahend.
       */
      public int readSignedSubtrahend() {
            return 128 - readByte();
      }

      /**
       * Reads a short with an additional value inscribed.
       * 
       * @return The short that was read.
       */
      public short readShortA() {
            int read = ((readByte() & 0xFF) << 8) | (readByte() - 128 & 0xFF);
            if (read > 32767) {
                  read -= 0x10000;
            }
            return (short) read;
      }
      
      public int readByte(boolean signed) {
            return readByte(signed, ByteModification.STANDARD);
      }
      
      public int readByte(ByteModification mod) {
            return readByte(true, mod);
      }
           
      public int readByte(boolean signed, ByteModification mod) {
            int value = readByte();
            switch (mod) {
                  case ADDITION:
                        value = value - 128;
                        break;
                        
                  case SUBTRACTION:
                        value = 128 - value;
                        break;
                        
                  case NEGATION:
                        value = -value;
                        break;
                        
                  case STANDARD:                        
                        break;
                  
            }
            return signed ? value : value & 0xFF;
      }
      
      public short readShort() {
            return readShort(true, ByteOrder.BIG, ByteModification.STANDARD);
      }
      
      public short readShort(boolean signed) {
            return readShort(signed, ByteOrder.BIG, ByteModification.STANDARD);
      }
      
      public short readShort(ByteOrder order) {
            return readShort(true, order, ByteModification.STANDARD);
      }
      
      public short readShort(boolean signed, ByteOrder order) {
            return readShort(signed, order, ByteModification.STANDARD);
      }
      
      public short readShort(ByteOrder order, ByteModification mod) {
            return readShort(true, order, mod);
      }
      
      public short readShort(ByteModification mod) {
            return readShort(true, ByteOrder.BIG, mod);
      }
      
      public short readShort(boolean signed, ByteModification mod) {
            return readShort(signed, ByteOrder.BIG, mod);
      }
      
      public short readShort(boolean signed, ByteOrder order, ByteModification mod) {
            int value = 0;
            switch(order) {
                  case BIG:
                        value |= readByte(false) << 8;
                        break;
                  case INVERSE:
                        break;
                  case LITTLE:
                        break;
                  case MIDDLE:
                        break;                  
            }           
            return 0;           
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
                  data[i] = readByte();
            }
            return data;
      }

      /**
       * Reads a little endian short with an inscribed addition.
       * 
       * @return The little endian short that was read.
       */
      public int readLEShortA() {
            int read = (readByte() - 128 & 0xFF) | ((readByte() & 0xFF) << 8);
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
      public byte[] readBytesA(int amount) {
            byte[] bytes = new byte[amount];
            for (int i = 0; i < amount; i++) {
                  bytes[i] = (byte) (readByte() + 128);
            }
            return bytes;
      }

      /**
       * Reads a little endian short with no modifications.
       * 
       * @return The little endian short that was read.
       */
      public int readLEShort() {
            int read = (readByte() & 0xFF) | ((readByte() & 0xFF) << 8);
            if (read > 32767) {
                  read -= 0x10000;
            }
            return (short) read;
      }
      
      @Override
      public String toString() {
            return String.format("[IncomingPacket] - [OPCODE= %d], [HEADER= %s], [SIZE= %d], [LENGTH= %d]", opcode, header.name(), size, length);
      }
}
