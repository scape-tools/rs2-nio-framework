package main.astraeus.net.packet.incoming.impl;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.incoming.IncomingPacket;
import main.astraeus.net.packet.incoming.IncomingPacketConstants;
import main.astraeus.net.packet.incoming.IncomingPacketListener;
import main.astraeus.net.packet.incoming.IncomingPacketOpcode;

/**
 * The {@link IncomingPacket} responsible for various clicking in-game.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode({
	IncomingPacketConstants.FOCUS_CHANGE, IncomingPacketConstants.IDLE_LOGOUT, 77,
	IncomingPacketConstants.CAMERA_MOVEMENT, 78, 36,
	226, 246, 148, 183, 230, 136, 189, 152, 200, 85, 165, 238, 150} )
public class DefaultPacketListener implements IncomingPacketListener {

	@Override
	public void handlePacket(Player player, IncomingPacket packet) {
		
	}

}
