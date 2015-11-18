package com.runescape.core.net.channel.message.incoming;

import com.runescape.core.game.model.entity.character.player.Player;
import com.runescape.core.net.channel.message.Packet;

/**
 * An interface which provides any Packet that implements this the ability
 * to be intercepted through the {@link SocketChannel} by the {@link PlayerIO} class
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
	void handleMessage(Player player, Packet packet);

}
