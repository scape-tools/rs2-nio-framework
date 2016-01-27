package main.astraeus.core.net.protocol.codec.game;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.astraeus.core.Configuration;
import main.astraeus.core.net.NetworkConstants;
import main.astraeus.core.net.channel.PlayerChannel;
import main.astraeus.core.net.packet.PacketHeader;
import main.astraeus.core.net.packet.incoming.IncomingPacket;
import main.astraeus.core.net.packet.incoming.IncomingPacketRegistration;
import main.astraeus.core.net.protocol.ProtocolConstants;
import main.astraeus.core.net.protocol.ProtocolStateDecoder;

/**
 * The decoder that decodes incoming {@Packet}s.
 * 
 * @author SeVen
 */
public final class GamePacketDecoder extends ProtocolStateDecoder {

      /**
       * The single logger for this class.
       */
      public static final Logger logger = Logger.getLogger(GamePacketDecoder.class.getName());

      /**
       * The default opcode.
       */
      private int opcode = -1;

      /**
       * The default length.
       * 
       * 0 or more Fixed byte length -1 Variable byte length -2 Variable short length
       */
      private int size = ProtocolConstants.VARIABLE_BYTE;

      /**
       * The state that this game packet decoder is in.
       */
      private GamePacketDecoderState state = GamePacketDecoderState.OPCODE;

      private PacketHeader header = PacketHeader.FIXED;

      @Override
      public void decode(PlayerChannel context) throws IOException {
            while (context.getBuffer().hasRemaining()) {
                  switch (state) {
                        case OPCODE:
                              opcode(context);
                              break;

                        case SIZE:
                              size(context);
                              break;

                        case PAYLOAD:
                              payload(context);
                              break;
                  }
            }
      }

      /**
       * The state that determines this packets opcode.
       * 
       * @param context The channel that this packet is coming from.
       */
      private void opcode(PlayerChannel context) {
            if (opcode == -1) {
                  opcode = context.getBuffer().get()
                              - context.getPlayer().getIsaacRandomPair().getEncoder().getNextValue()
                              & 0xFF;
                  size = NetworkConstants.PACKET_SIZES[opcode];
                  
                  if (size == -1) {
                        header = PacketHeader.VARIABLE_BYTE;
                  } else if (size == -2) {
                        header = PacketHeader.VARIABLE_SHORT;
                  } else {
                        header = PacketHeader.FIXED;
                  }
                
            }
            state = size == -1 || size == -2 ? GamePacketDecoderState.SIZE : GamePacketDecoderState.PAYLOAD;
      }

      /**
       * The state that determines the type of packet.
       * 
       * @param context The channel that this packet is coming from.
       */
      private void size(PlayerChannel context) {
            int bytes = size == -1 ? Byte.BYTES : Short.BYTES;
            
            if (context.getBuffer().remaining() >= bytes) {
                  size = 0;
                  
                  for(int i = 0; i < bytes; i++) {
                        size |= context.getBuffer().get() & 0xFF << 8 * (bytes -1 - i);
                  }
            }
            
            state = GamePacketDecoderState.PAYLOAD;
      }

      /**
       * The state that decodes the payload of this packet.
       * 
       * @param context The session this packet is coming from.
       */
      private void payload(PlayerChannel context) {
            try {
                  if (context.getBuffer().remaining() >= size) {
                  byte[] payloadLength = new byte[size];
                  context.getBuffer().get(payloadLength);

                  ByteBuffer packetPayload = ByteBuffer.allocate(size);

                  packetPayload.put(payloadLength);
                  packetPayload.flip();

                  IncomingPacket packet = new IncomingPacket(opcode, header, packetPayload);

                  IncomingPacketRegistration.dispatchToListener(packet, context.getPlayer());

                  if (Configuration.SERVER_DEBUG && context.getPlayer().isServerDebug()
                              && packet.getOpcode() != 0)
                        logger.log(Level.INFO, packet.toString());

                  }
                  
            } finally {
                  state = GamePacketDecoderState.OPCODE;
                  opcode = -1;
                  size = -1;
            }
      }

}
