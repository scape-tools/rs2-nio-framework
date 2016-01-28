package main.astraeus.net.packet.incoming.impl;

import main.astraeus.content.clicking.ButtonClick;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.PacketReader;
import main.astraeus.net.packet.incoming.IncomingPacket;
import main.astraeus.net.packet.incoming.IncomingPacketListener;
import main.astraeus.net.packet.incoming.IncomingPacketOpcode;

/**
 * The packet responsible for clicking in-game buttons.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode(185)
public class ButtonClickPacketListener implements IncomingPacketListener {

      @Override
      public void handlePacket(Player player, IncomingPacket packet) {

            final PacketReader reader = packet.getReader();

            final int button = reader.readShort();
            
            ButtonClick.handleAction(player, button);
      }

}
