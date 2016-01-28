package main.astraeus.net.packet.incoming;

import main.astraeus.game.model.entity.mobile.player.Player;

/**
 * An interface which provides any Packet that implements this the ability
 * to be intercepted through the {@link SocketChannel} by the {@link PlayerChannel} class
 * as a message.
 * 
 * @author SeVen
 */
public interface IncomingPacketListener {
	
	/**
	 * Executes the packet that has been received.
	 * 
	 * @param player
	 * 		The player receiving this packet.
	 * @param packet
	 * 		The packet that has been received.
	 */
	void handlePacket(Player player, IncomingPacket packet);

}
