package main.astraeus.net.packet.incoming.impl;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.object.GameObjectManager;
import main.astraeus.net.packet.incoming.IncomingPacket;
import main.astraeus.net.packet.incoming.IncomingPacketConstants;
import main.astraeus.net.packet.incoming.IncomingPacketListener;
import main.astraeus.net.packet.incoming.IncomingPacketOpcode;

@IncomingPacketOpcode({IncomingPacketConstants.ENTER_REGION, IncomingPacketConstants.LOADED_REGION})
public class RegionalUpdatePacketListener implements IncomingPacketListener {

      @Override
      public void handleMessage(Player player, IncomingPacket packet) {

            switch (packet.getOpcode()) {

                  case IncomingPacketConstants.ENTER_REGION:
                        GameObjectManager.updateRegionalObjects(player);
                        break;

                  case IncomingPacketConstants.LOADED_REGION:
                        break;

            }

      }

}
