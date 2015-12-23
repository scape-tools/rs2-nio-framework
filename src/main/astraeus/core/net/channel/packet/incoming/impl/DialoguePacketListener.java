package main.astraeus.core.net.channel.packet.incoming.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.channel.packet.IncomingPacket;
import main.astraeus.core.net.channel.packet.incoming.IncomingPacketListener;
import main.astraeus.core.net.channel.packet.incoming.IncomingPacketOpcode;
import main.astraeus.core.net.channel.packet.outgoing.SendClearScreen;

@IncomingPacketOpcode( 40 )
public final class DialoguePacketListener implements IncomingPacketListener {

	@Override
	public void handleMessage(Player player, IncomingPacket packet) {
		if (player.getDialogue().getDialogueStage() >= 0 && player.getDialogue() != null) {
			player.getDialogue().setDialogueStage(player.getDialogue().getDialogueStage() + 1);
			player.getDialogue().sendDialogues(player);
			System.out.println(player.getDialogue().getDialogueStage());
		} else {
			player.send(new SendClearScreen());
		}		
	}

}
