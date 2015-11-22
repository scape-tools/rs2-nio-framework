package com.astraeus.core.net.channel.packet.incoming.impl;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.IncomingPacket;
import com.astraeus.core.net.channel.packet.incoming.IncomingPacketConstants;
import com.astraeus.core.net.channel.packet.incoming.IncomingPacketListener;
import com.astraeus.core.net.channel.packet.incoming.IncomingPacketOpcode;

/**
 * The incoming {@link IncomingPacket}'s that are known as silent packets.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode({
	IncomingPacketConstants.FOCUS_CHANGE, IncomingPacketConstants.IDLE_LOGOUT, 77,
	IncomingPacketConstants.CAMERA_MOVEMENT, 78, 36,
	226, 246, 148, 183, 230, 136, 189, 152, 200, 85, 165, 238, 150} )
public class SilentPacketListener implements IncomingPacketListener {

	@Override
	public void handleMessage(Player player, IncomingPacket packet) {
		
	}

}
