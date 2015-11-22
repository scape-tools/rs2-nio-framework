package com.astraeus.core.net.channel.packet.incoming.impl;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.model.entity.object.GameObjectManager;
import com.astraeus.core.net.channel.packet.IncomingPacket;
import com.astraeus.core.net.channel.packet.incoming.IncomingPacketConstants;
import com.astraeus.core.net.channel.packet.incoming.IncomingPacketListener;
import com.astraeus.core.net.channel.packet.incoming.IncomingPacketOpcode;

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
