package com.astraeus.core.net.channel.message.incoming.impl;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.message.IncomingPacketOpcode;
import com.astraeus.core.net.channel.message.Packet;
import com.astraeus.core.net.channel.message.incoming.IncomingPacketListener;

@IncomingPacketOpcode( 40 )
public final class DialoguePacketListener implements IncomingPacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		if (player.getDialogue().getDialogueStage() >= 0 && player.getDialogue() != null) {
			player.getDialogue().setDialogueStage(player.getDialogue().getDialogueStage() + 1);
			player.getDialogue().sendDialogues(player);
		} else {
			player.getPacketSender().sendCloseInterface();
		}		
	}

}
