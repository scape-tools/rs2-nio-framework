package com.astraeus.core.net.channel.message.incoming.impl;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.message.IncomingPacketOpcode;
import com.astraeus.core.net.channel.message.Packet;
import com.astraeus.core.net.channel.message.incoming.IncomingPacketConstants;
import com.astraeus.core.net.channel.message.incoming.IncomingPacketListener;

/**
 * The incoming {@link Packet}'s that are known as silent packets.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode({
	IncomingPacketConstants.FOCUS_CHANGE, IncomingPacketConstants.IDLE_LOGOUT, 77,
	IncomingPacketConstants.CAMERA_MOVEMENT, 78, 36,
	226, 246, 148, 183, 230, 136, 189, 152, 200, 85, 165, 238, 150} )
public class SilentPacketListener implements IncomingPacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		
	}

}
