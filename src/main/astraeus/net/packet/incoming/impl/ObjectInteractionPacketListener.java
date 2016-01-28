package main.astraeus.net.packet.incoming.impl;

import main.astraeus.content.clicking.objects.ObjectFirstClick;
import main.astraeus.content.clicking.objects.ObjectSecondClick;
import main.astraeus.game.model.Position;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.object.GameObject;
import main.astraeus.net.packet.PacketReader;
import main.astraeus.net.packet.incoming.IncomingPacket;
import main.astraeus.net.packet.incoming.IncomingPacketConstants;
import main.astraeus.net.packet.incoming.IncomingPacketListener;
import main.astraeus.net.packet.incoming.IncomingPacketOpcode;
import main.astraeus.net.protocol.codec.ByteModification;
import main.astraeus.net.protocol.codec.ByteOrder;

/**
 * The {@link IncomingPacket} responsible for clicking various options of an
 * in-game object.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode({132, 252, 70})
public final class ObjectInteractionPacketListener implements IncomingPacketListener {

      @Override
      public void handlePacket(Player player, IncomingPacket packet) {
            
            final PacketReader reader = packet.getReader();
            
            switch (packet.getOpcode()) {

                  case IncomingPacketConstants.FIRST_CLICK_OBJECT:
                        handleFirstClickObject(player, reader);
                        break;

                  case IncomingPacketConstants.SECOND_CLICK_OBJECT:
                        handleSecondClickObject(player, reader);
                        break;
            }

      }
      
      /**
       * Handles the event when a {@code player} uses the first option on an in-game object.
       * 
       *    @param player
       *      The player performing the action.
       *    
       *    @param reader
       *       The reader used to read the packet information.
       */
      private void handleFirstClickObject(Player player, PacketReader reader) {
            int x = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
            int id = reader.readShort(false);                        
            int y = reader.readShort(false, ByteModification.ADDITION);
            
            GameObject object = new GameObject(id, new Position(x, y, player.getPosition().getHeight()));

            if (player.getPosition().isWithinInteractionDistance(object.getPosition())) {
                  ObjectFirstClick.handleAction(player, object);  
            }

      }
      
      /**
       * Handles the event when a {@code player} uses the second option on an in-game object.
       * 
       *    @param player
       *      The player performing the action.
       *    
       *    @param reader
       *       The reader used to read the packet information.
       */
      private void handleSecondClickObject(Player player, PacketReader reader) {
            int id = reader.readShort(ByteOrder.LITTLE, ByteModification.STANDARD);
            int y = reader.readShort(ByteOrder.LITTLE);
            int x = reader.readShort(ByteModification.ADDITION);
            
            GameObject object = new GameObject(id, new Position(x, y, player.getPosition().getHeight()));

            if (player.getPosition().isWithinInteractionDistance(object.getPosition())) {
                  ObjectSecondClick.handleAction(player, object);  
            }
      }

}
