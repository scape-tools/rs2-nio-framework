package main.astraeus.core.net.packet.incoming.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.Player.Attributes;
import main.astraeus.core.game.pulse.PulseScheduler;
import main.astraeus.core.game.pulse.impl.InteractionDistancePulse;
import main.astraeus.core.game.pulse.impl.InteractionDistancePulse.InteractionType;
import main.astraeus.core.net.packet.incoming.IncomingPacket;
import main.astraeus.core.net.packet.incoming.IncomingPacketConstants;
import main.astraeus.core.net.packet.incoming.IncomingPacketListener;
import main.astraeus.core.net.packet.incoming.IncomingPacketOpcode;

/**
 * The packet opcodes which this listener implementation handles.
 */
@IncomingPacketOpcode({132, 252, 70})
public final class ObjectActionPacketListener implements IncomingPacketListener {

      @Override
      public void handleMessage(Player player, IncomingPacket packet) {
            switch (packet.getOpcode()) {

                  case IncomingPacketConstants.FIRST_CLICK_OBJECT:
                        int objectX = packet.readLittleEndianShortAddition();
                        int objectIndex = packet.getBuffer().getShort();
                        int objectY = packet.readAdditionalShort();

                        player.getAttributes().put(Attributes.CLICK_Y, objectY);
                        player.getAttributes().put(Attributes.CLICK_INDEX, objectIndex);
                        player.getAttributes().put(Attributes.CLICK_X, objectX);
                        PulseScheduler.getInstance()
                                    .register(new InteractionDistancePulse(player,
                                                InteractionType.OBJECT_INTERACTION_FIRST_CLICK),
                                    true);
                        break;

                  case IncomingPacketConstants.SECOND_CLICK_OBJECT:
                        objectIndex = packet.readLittleEndianShortAddition();
                        objectY = packet.readLittleEndianShort();
                        objectX = packet.readAdditionalShort();

                        player.getAttributes().put(Attributes.CLICK_Y, objectY);
                        player.getAttributes().put(Attributes.CLICK_INDEX, objectIndex);
                        player.getAttributes().put(Attributes.CLICK_X, objectX);
                        PulseScheduler.getInstance()
                                    .register(new InteractionDistancePulse(player,
                                                InteractionType.OBJECT_INTERACTION_SECOND_CLICK),
                                    true);
                        break;
            }

      }

}
