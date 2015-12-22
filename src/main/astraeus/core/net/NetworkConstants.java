package main.astraeus.core.net;

import main.astraeus.core.net.channel.packet.incoming.IncomingPacketListener;

/**
 * Class which contains network-related constants.
 * @author SeVen
 */
public class NetworkConstants {

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
	public static final int PACKET_SIZES[] = {  0, 0, 0, 1, -1, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 8, 0, 6, 2, 2, 0, 0, 2, 0, 6, 0, 12, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 8, 4, 0, 0, 2, 2, 6, 0, 6, 0, -1, 0, 0, 0, 0, 0, 0, 0, 12,
		0, 0, 0, 0, 8, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 2, 2, 8, 6,
		0, -1, 0, 6, 0, 0, 0, 0, 0, 1, 4, 6, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0,
		-1, 0, 0, 13, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0,
		0, 1, 0, 6, 0, 0, 0, -1, 0, 2, 6, 0, 4, 6, 8, 0, 6, 0, 0, 0, 2, 0,
		0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 1, 2, 0, 2, 6, 0, 0, 0, 0, 0, 0,
		0, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 3, 0,
		2, 0, 0, 8, 1, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0,
		0, 4, 0, 4, 0, 0, 0, 7, 8, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, -1, 0, 6,
		0, 1, 0, 0, 0, 6, 0, 6, 8, 1, 0, 0, 4, 0, 0, 0, 0, -1, 0, -1, 4, 0,
		0, 6, 6, 0, 0, 0
	};
}
