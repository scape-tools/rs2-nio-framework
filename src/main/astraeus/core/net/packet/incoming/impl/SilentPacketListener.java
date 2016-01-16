package main.astraeus.core.net.packet.incoming.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.incoming.IncomingPacket;
import main.astraeus.core.net.packet.incoming.IncomingPacketConstants;
import main.astraeus.core.net.packet.incoming.IncomingPacketListener;
import main.astraeus.core.net.packet.incoming.IncomingPacketOpcode;

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
