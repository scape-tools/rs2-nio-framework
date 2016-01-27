package main.astraeus.net.packet.incoming.impl;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.player.Player.Attributes;
import main.astraeus.game.pulse.PulseScheduler;
import main.astraeus.game.pulse.impl.InteractionDistancePulse;
import main.astraeus.game.pulse.impl.InteractionDistancePulse.InteractionType;
import main.astraeus.net.packet.PacketReader;
import main.astraeus.net.packet.incoming.IncomingPacket;
import main.astraeus.net.packet.incoming.IncomingPacketConstants;
import main.astraeus.net.packet.incoming.IncomingPacketListener;
import main.astraeus.net.packet.incoming.IncomingPacketOpcode;
import main.astraeus.net.protocol.codec.ByteModification;
import main.astraeus.net.protocol.codec.ByteOrder;

/**
 * The packet opcodes which this listener implementation handles.
 */
@IncomingPacketOpcode({132, 252, 70})
public final class ObjectInteractionPacketListener implements IncomingPacketListener {

      @Override
      public void handleMessage(Player player, IncomingPacket packet) {
            
            final PacketReader reader = packet.getReader();
            
            switch (packet.getOpcode()) {

                  case IncomingPacketConstants.FIRST_CLICK_OBJECT:
                        int x = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
                        int id = reader.readShort(false);                        
                        int y = reader.readShort(false, ByteModification.ADDITION);

                        System.out.println("x: " + x + " id: " + id + " y: " + y);
                        
                        player.getAttributes().put(Attributes.CLICK_Y, y);
                        player.getAttributes().put(Attributes.CLICK_INDEX, id);
                        player.getAttributes().put(Attributes.CLICK_X, x);
                        PulseScheduler.getInstance()
                                    .register(new InteractionDistancePulse(player,
                                                InteractionType.OBJECT_INTERACTION_FIRST_CLICK),
                                    true);
                        break;

                  case IncomingPacketConstants.SECOND_CLICK_OBJECT:
//                        objectIndex = packet.readLEShortA();
//                        objectY = packet.readLEShort();
//                        objectX = packet.readShortA();
//
//                        player.getAttributes().put(Attributes.CLICK_Y, objectY);
//                        player.getAttributes().put(Attributes.CLICK_INDEX, objectIndex);
//                        player.getAttributes().put(Attributes.CLICK_X, objectX);
//                        PulseScheduler.getInstance()
//                                    .register(new InteractionDistancePulse(player,
//                                                InteractionType.OBJECT_INTERACTION_SECOND_CLICK),
//                                    true);
                        break;
            }

      }

}
