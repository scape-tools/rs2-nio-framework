package main.astraeus.core.net.packet.incoming.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.Rights;
import main.astraeus.core.net.packet.incoming.IncomingPacket;
import main.astraeus.core.net.packet.incoming.IncomingPacketConstants;
import main.astraeus.core.net.packet.incoming.IncomingPacketListener;
import main.astraeus.core.net.packet.incoming.IncomingPacketOpcode;
import main.astraeus.utility.InterfaceConstants;

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
