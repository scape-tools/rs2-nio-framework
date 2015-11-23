package com.astraeus.core.net.channel.packet.incoming.impl;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.IncomingPacket;
import com.astraeus.core.net.channel.packet.incoming.IncomingPacketListener;
import com.astraeus.core.net.channel.packet.incoming.IncomingPacketOpcode;
import com.astraeus.core.net.channel.packet.outgoing.ClearScreenPacket;

@IncomingPacketOpcode( 40 )
public final class DialoguePacketListener implements IncomingPacketListener {

	@Override
	public void handleMessage(Player player, IncomingPacket packet) {
		if (player.getDialogue().getDialogueStage() >= 0 && player.getDialogue() != null) {
			player.getDialogue().setDialogueStage(player.getDialogue().getDialogueStage() + 1);
			player.getDialogue().sendDialogues(player);
		} else {
			player.write(new ClearScreenPacket());
		}		
	}

}
