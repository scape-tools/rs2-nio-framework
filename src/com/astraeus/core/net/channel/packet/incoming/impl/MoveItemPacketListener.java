package com.astraeus.core.net.channel.packet.incoming.impl;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.model.entity.mobile.player.Rights;
import com.astraeus.core.net.channel.packet.IncomingPacket;
import com.astraeus.core.net.channel.packet.incoming.IncomingPacketConstants;
import com.astraeus.core.net.channel.packet.incoming.IncomingPacketListener;
import com.astraeus.core.net.channel.packet.incoming.IncomingPacketOpcode;
import com.astraeus.core.utility.InterfaceConstants;

/**
 * The {@link IncomingPacket} that is responsible for allowing a player to move items from
 * one slot to another inside a container.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode(IncomingPacketConstants.MOVE_ITEM)
public class MoveItemPacketListener implements IncomingPacketListener {

	@Override
	public void handleMessage(Player player, IncomingPacket packet) {
		int interfaceId = packet.readLittleEndianShortAddition();		
		packet.readNegatedByte();		
		int from = packet.readLittleEndianShortAddition();		
		int to = packet.readLittleEndianShort();
		
		if(player.getRights().greaterOrEqual(Rights.DEVELOPER)) {
			player.sendMessage("[MoveItem] - InterfaceId: " + interfaceId + " from: " + from + " to: " + to);
		}
		
		switch(interfaceId) {
		
		case InterfaceConstants.INVENTORY:
			player.getInventoryContainer().swapItem(from, to);
			break;
			
		}
		
	}

}
