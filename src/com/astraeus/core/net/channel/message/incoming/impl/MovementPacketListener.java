package com.astraeus.core.net.channel.message.incoming.impl;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.message.IncomingPacketOpcode;
import com.astraeus.core.net.channel.message.Packet;
import com.astraeus.core.net.channel.message.incoming.IncomingPacketListener;

/**
 * The packet opcodes which this listener implementation handles.
 */
@IncomingPacketOpcode ({ 248, 98, 164 })
/**
 * @author Dylan Vicchiarelli
 *
 * Handles the action of walking or running on the global map palate.
 */
public class MovementPacketListener implements IncomingPacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		
		int packetLength = packet.getLength();
		
		player.getPacketSender().sendCloseInterface();

		if (packet.getOpcode() == 248) {
			packetLength -= 14;
		}

		final int steps = (packetLength - 5) / 2;
		final int[][] path = new int[steps][2];
		final int targetX = packet.readLittleEndianShortAddition();
		for (int i = 0; i < steps; i++) {
			path[i][0] = packet.getBuffer().get();
			path[i][1] = packet.getBuffer().get();
		}
		final int targetY = packet.readLittleEndianShort();
		player.getMovement().resetMovement();
		player.getMovement().setRunningQueueEnabled(packet.readNegatedByte() == 1);
		player.getMovement().addExternalStep(targetX, targetY);
		for (int i = 0; i < steps; i++) {
			path[i][0] += targetX;
			path[i][1] += targetY;
			player.getMovement().addExternalStep(path[i][0], path[i][1]);
		}
		player.getMovement().finishMovement();
	}
}
