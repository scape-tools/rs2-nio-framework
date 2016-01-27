package main.astraeus.net.packet.outgoing.impl;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.PacketWriter;
import main.astraeus.net.packet.outgoing.OutgoingPacket;

/**
 * The {@link OutgoingPacket} that logs a player out of the game.
 * 
 * @author SeVen
 */
public class SendLogout extends OutgoingPacket {

      /**
       * Creates a new {@link SendLogout}.
       */
      public SendLogout() {
            super(109, 1);
      }

      @Override
      public PacketWriter encode(Player player) {
            player.getContext().prepare(this, writer);
            return writer;
      }

}
