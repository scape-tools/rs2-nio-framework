package main.astraeus.core.net.packet.incoming.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.incoming.IncomingPacket;
import main.astraeus.core.net.packet.incoming.IncomingPacketListener;
import main.astraeus.core.net.packet.incoming.IncomingPacketOpcode;
import main.astraeus.core.net.packet.outgoing.impl.SendClearScreen;

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

            player.send(new SendClearScreen());

            int packetLength = packet.getLength();

            if (packet.getOpcode() == 248) {
                  packetLength -= 14;
            }

            final int steps = (packetLength - 5) / 2;
            final int[][] path = new int[steps][2];
            final int targetX = packet.readLEShortA();
            for (int i = 0; i < steps; i++) {
                  path[i][0] = packet.getPayload().get();
                  path[i][1] = packet.getPayload().get();
            }
            final int targetY = packet.readLEShort();
            player.getMovement().resetMovement();
            player.getMovement().setRunningQueueEnabled(packet.readByteI() == 1);
            player.getMovement().addExternalStep(targetX, targetY);
            for (int i = 0; i < steps; i++) {
                  path[i][0] += targetX;
                  path[i][1] += targetY;
                  player.getMovement().addExternalStep(path[i][0], path[i][1]);
            }
            player.getMovement().finishMovement();
      }
}
