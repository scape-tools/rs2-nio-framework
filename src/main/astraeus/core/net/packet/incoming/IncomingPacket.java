package main.astraeus.core.net.packet.incoming;

import java.nio.ByteBuffer;

import main.astraeus.core.net.packet.PacketHeader;
import main.astraeus.core.net.packet.PacketReader;

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
       * The reader that will read this packet.
       */
      private final PacketReader reader;

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
            this.reader = new PacketReader(this);
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
       * 
       * @return The number of elements remaining in the payload.
       */
      public int getSize() {
            return payload.remaining();
      }
      
      /**
       * Gets the length of this packet.
       * 
       * @return The payload capacity.
       */
      public int getLength() {
            return payload.capacity();
      }     

      
      /**
       * Gets the reader for this packet.
       * 
       * @return The reader.
       */
      public PacketReader getReader() {
            return reader;
      }

      @Override
      public String toString() {
            return String.format("[IncomingPacket] - [OPCODE= %d], [HEADER= %s], [SIZE= %d], [LENGTH= %d]", opcode, header.name(), size, length);
      }
}
