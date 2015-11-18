package com.runescape.core.net.channel.message.incoming.impl;

import com.runescape.core.game.model.entity.mobile.player.Player;
import com.runescape.core.net.channel.message.IncomingPacketOpcode;
import com.runescape.core.net.channel.message.Packet;
import com.runescape.core.net.channel.message.incoming.IncomingPacketListener;

/**
 * The packet responsible for clicking in-game buttons.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode( 185 )
public class ButtonClickPacketListener implements IncomingPacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		final int button = packet.getBuffer().getShort();
		
		System.out.println(player.getPacketSender().sendMessage("ButtonId: " + button));
		
		switch(button) {
		
		// walk
		case 152:
			player.getMovement().setRunning(false);
			player.getMovement().setRunningQueueEnabled(false);
			break;
		
		// run
		case 153:
			player.getMovement().setRunning(true);
			player.getMovement().setRunningQueueEnabled(true);
			break;
		
		// logout
		case 2458:
			player.getPacketSender().sendLogout();
			player.getContext().close();
			break;		
		
		}
	}

}
