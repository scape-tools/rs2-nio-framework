package com.astraeus.core.net.channel.message.incoming.impl;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.model.entity.object.GameObjectManager;
import com.astraeus.core.net.channel.message.IncomingPacketOpcode;
import com.astraeus.core.net.channel.message.Packet;
import com.astraeus.core.net.channel.message.incoming.IncomingPacketConstants;
import com.astraeus.core.net.channel.message.incoming.IncomingPacketListener;

@IncomingPacketOpcode( { IncomingPacketConstants.ENTER_REGION,  IncomingPacketConstants.LOADED_REGION } )
public class RegionalUpdatePacketListener implements IncomingPacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		
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
