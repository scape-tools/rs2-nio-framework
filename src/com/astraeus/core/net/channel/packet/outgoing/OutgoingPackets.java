package com.astraeus.core.net.channel.packet.outgoing;

import com.astraeus.core.game.GameConstants;
import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.outgoing.impl.ChatInterfacePacket;
import com.astraeus.core.net.channel.packet.outgoing.impl.InterfaceAnimationPacket;
import com.astraeus.core.net.channel.packet.outgoing.impl.NpcDialogueHeadPacket;
import com.astraeus.core.net.channel.packet.outgoing.impl.PlayerDialogueHeadPacket;
import com.astraeus.core.net.channel.packet.outgoing.impl.SendStringPacket;
import com.astraeus.core.net.channel.packet.outgoing.impl.SideBarInterfacePacket;

/**
 * The class that contains various methods for sending {@link OutgoingPacket}'s.
 * 
 * @author SeVen
 */
public class OutgoingPackets {

	/**
	 * The player that is sending the packet.
	 */
	private Player player;

	/**
	 * Constructs a new {@link OutgoingPackets} which sends
	 * a packet to the client.
	 * 
	 * @param player
	 * 		The player that is sending the packet.
	 */
	public OutgoingPackets(Player player) {
		this.player = player;
	}
	
	public void sendString(String string, int widget) {
		player.write(new SendStringPacket(string, widget));
	}
	
	public void sendChatBoxInterface(int interfaceId) {
		player.write(new ChatInterfacePacket(interfaceId));
	}
	
	public void sendInterfaceAnimation(int interfaceId, int animationId) {
		player.write(new InterfaceAnimationPacket(interfaceId, animationId));
	}
	
	public void sendDialogueNpcHead(int npcId, int interfaceId) {
		player.write(new NpcDialogueHeadPacket(npcId, interfaceId));
	}
	
	public void sendDialoguePlayerHead(int interfaceId) {
		player.write(new PlayerDialogueHeadPacket(interfaceId));
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
