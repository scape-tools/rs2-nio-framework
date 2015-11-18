package com.runescape.core.net.channel.message.incoming.impl;

import com.runescape.core.game.model.entity.mobile.player.Player;
import com.runescape.core.net.channel.message.IncomingPacketOpcode;
import com.runescape.core.net.channel.message.Packet;
import com.runescape.core.net.channel.message.incoming.IncomingPacketListener;

@IncomingPacketOpcode(103)
public class CommandPacketListener implements IncomingPacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		final int messageLength = packet.getLength() - 1;

		byte message[] = packet.readBytes(messageLength);
		
		String command[] = new String(message).split(" ");
		
		switch(command[0]) {
		
		case "test":
			System.out.println(player.getPacketSender().sendMessage("This worked!"));
			break;
			
		default:
			System.out.println("Unknown command: " + command);
			break;
		}
	}

}
