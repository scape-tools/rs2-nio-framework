package main.astraeus.core.net;

import main.astraeus.core.net.packet.incoming.IncomingPacketListener;

/**
 * Class which contains network-related constants.
 * 
 * @author SeVen
 */
public class NetworkConstants {

      /**
       * The rate in milliseconds in which the network processes logic.
       */
      public static final int NETWORK_CYCLE_RATE = 30;

      /**
       * The amount of incoming packets that can be processed at a time.
       */
      public static final int DECODE_LIMIT = 30;

      /**
       * An array of incoming packets.
       */
      public static final IncomingPacketListener[] PACKETS = new IncomingPacketListener[257];

      /**
       * The possible incoming packet sizes for the #317 protocol.
       */
      public static final int PACKET_SIZES[] = new int[257];
}
