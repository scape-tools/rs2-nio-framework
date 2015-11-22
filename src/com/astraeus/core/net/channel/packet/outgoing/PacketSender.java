package com.astraeus.core.net.channel.packet.outgoing;

import com.astraeus.core.game.GameConstants;
import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.outgoing.impl.SideBarInterfacePacket;

/**
 * Contains outgoing packets that can be sent to the Client.
 * 
 * @author SeVen
 */
public class PacketSender {

	/**
	 * The player that is sending the packet.
	 */
	private Player player;

	/**
	 * Constructs a new {@link PacketSender} which sends
	 * a packet to the client.
	 * 
	 * @param player
	 * 		The player that is sending the packet.
	 */
	public PacketSender(Player player) {
		this.player = player;
	}

	/**
	 * Creates all the in-game tabs for a player.
	 * 
	 * @param The instance of this encoder.
	 */
	public void sendTabs() {		
		for(int index = 0; index < GameConstants.SIDE_BARS.length; index++) {
			player.write(new SideBarInterfacePacket(index, GameConstants.SIDE_BARS[index]));
		}
	}

}
