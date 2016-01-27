package main.astraeus.net.packet.incoming.impl;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.PacketReader;
import main.astraeus.net.packet.incoming.IncomingPacket;
import main.astraeus.net.packet.incoming.IncomingPacketListener;
import main.astraeus.net.packet.incoming.IncomingPacketOpcode;
import main.astraeus.net.packet.outgoing.impl.SendClearScreen;
import main.astraeus.net.protocol.codec.ByteModification;
import main.astraeus.net.protocol.codec.ByteOrder;

/**
 * The packet opcodes which this listener implementation handles.
 */
@IncomingPacketOpcode({248, 98, 164})
/**
 * @author Dylan Vicchiarelli
 *
 *         Handles the action of walking or running on the global map palate.
 */
public class WalkingPacketListener implements IncomingPacketListener {

      @Override
      public void handleMessage(Player player, IncomingPacket packet) {
            
            final PacketReader reader = packet.getReader();

            player.send(new SendClearScreen());

            int packetLength = packet.getLength();

            if (packet.getOpcode() == 248) {
                  packetLength -= 14;
            }

            final int steps = (packetLength - 5) / 2;
            final int[][] path = new int[steps][2];
            final int targetX = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
            for (int i = 0; i < steps; i++) {
                  path[i][0] = reader.readByte();
                  path[i][1] = reader.readByte();
            }
            final int targetY = reader.readShort(ByteOrder.LITTLE);
            player.getMovement().resetMovement();
            player.getMovement().setRunningQueueEnabled(reader.readByte(ByteModification.NEGATION) == 1);
            player.getMovement().addExternalStep(targetX, targetY);
            for (int i = 0; i < steps; i++) {
                  path[i][0] += targetX;
                  path[i][1] += targetY;
                  player.getMovement().addExternalStep(path[i][0], path[i][1]);
            }
            player.getMovement().finishMovement();
      }
}
