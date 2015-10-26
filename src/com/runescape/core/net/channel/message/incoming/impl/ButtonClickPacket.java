package com.runescape.core.net.channel.message.incoming.impl;

import com.runescape.core.game.model.entity.character.player.Player;
import com.runescape.core.net.channel.message.Packet;
import com.runescape.core.net.channel.message.incoming.PacketListener;

/**
 * The packet responsible for clicking in-game buttons.
 * 
 * @author SeVen
 */
public class ButtonClickPacket implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		int id = packet.getShort();
		
		player.getPacketSender().sendMessage("ButtonClick: " + id);
		
		switch(id) {
		
		}
		
	}

}
