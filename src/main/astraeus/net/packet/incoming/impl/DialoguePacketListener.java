package main.astraeus.net.packet.incoming.impl;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.incoming.IncomingPacket;
import main.astraeus.net.packet.incoming.IncomingPacketListener;
import main.astraeus.net.packet.incoming.IncomingPacketOpcode;
import main.astraeus.net.packet.outgoing.impl.SendClearScreen;

@IncomingPacketOpcode(40)
public final class DialoguePacketListener implements IncomingPacketListener {

      @Override
      public void handlePacket(Player player, IncomingPacket packet) {
            if (player.getDialogue().getDialogueStage() >= 0 && player.getDialogue() != null) {
                  player.getDialogue()
                              .setDialogueStage(player.getDialogue().getDialogueStage() + 1);
                  player.getDialogue().sendDialogues(player);
            } else {
                  player.send(new SendClearScreen());
            }
      }

}
