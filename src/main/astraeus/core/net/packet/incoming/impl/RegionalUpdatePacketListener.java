package main.astraeus.core.net.packet.incoming.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.object.GameObjectManager;
import main.astraeus.core.net.packet.incoming.IncomingPacket;
import main.astraeus.core.net.packet.incoming.IncomingPacketConstants;
import main.astraeus.core.net.packet.incoming.IncomingPacketListener;
import main.astraeus.core.net.packet.incoming.IncomingPacketOpcode;

@IncomingPacketOpcode( { IncomingPacketConstants.ENTER_REGION,  IncomingPacketConstants.LOADED_REGION } )
public class RegionalUpdatePacketListener implements IncomingPacketListener {

	@Override
	public void handleMessage(Player player, IncomingPacket packet) {
		
		switch(packet.getOpcode()) {
		
		case IncomingPacketConstants.ENTER_REGION:
			GameObjectManager.updateRegionalObjects(player);
			System.out.println("Entered region");
			break;
			
		case IncomingPacketConstants.LOADED_REGION:
			System.out.println("Loaded region");
			break;
		
		}
		
	}
	
}
