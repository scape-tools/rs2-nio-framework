package main.astraeus.net.channel.events;

import java.io.IOException;

import main.astraeus.net.channel.ChannelEvent;
import main.astraeus.net.channel.PlayerChannel;
import main.astraeus.net.packet.PacketHeader;
import main.astraeus.net.packet.PacketWriter;

public final class PrepareChannelEvent extends ChannelEvent {

      /**
       * The header for this message.
       */
      private final PacketHeader header;

      /**
       * The buffer for this message.
       */
      private final PacketWriter buffer;

      /**
       * The opcode for this message.
       */
      private final int opcode;

      /**
       * Creates a new {@link PrepareChannelEvent} with a default {@code PacketHeader} of
       * {@code EMPTY}.
       * 
       * @param opcode The opcode for this message.
       * 
       * @param header The header for this message.
       * 
       * @param reader The buffer for this message.
       */
      public PrepareChannelEvent(int opcode, PacketWriter reader) {
            this(opcode, PacketHeader.EMPTY, reader);
      }

      /**
       * Creates a new {@link PrepareChannelEvent}.
       * 
       * @param opcode The opcode for this message.
       * 
       * @param header The header for this message.
       * 
       * @param buffer The buffer for this message.
       */
      public PrepareChannelEvent(int opcode, PacketHeader header, PacketWriter buffer) {
            this.opcode = opcode;
            this.header = header;
            this.buffer = buffer;

      }

      @Override
      public void execute(PlayerChannel context) throws IOException {
            /*
             * If a packet does not have an empty heading then the opcode must be encrypted with a
             * cryptographic cipher. After the encryption if the packet's heading is defined as a
             * byte or a short the length must be indicated by that primitive.
             */
            if (!header.equals(PacketHeader.EMPTY)) {
                  buffer.write(opcode + context.getPlayer().getIsaacRandomPair().getDecoder()
                              .getNextValue());

                  if (header.equals(PacketHeader.VARIABLE_BYTE)) {
                        buffer.setLength(buffer.getBuffer().position());
                        buffer.write(0);
                  } else if (header.equals(PacketHeader.VARIABLE_SHORT)) {
                        buffer.setLength(buffer.getBuffer().position());
                        buffer.writeShort(0);
                  }
            }
      }
}
