package com.astraeus.core.game.model.entity.mobile.npc.update;

import com.astraeus.core.game.model.entity.Position;
import com.astraeus.core.game.model.entity.UpdateFlags.UpdateFlag;
import com.astraeus.core.game.model.entity.mobile.npc.Npc;
import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.model.entity.mobile.player.Player.Attributes;
import com.astraeus.core.net.channel.packet.PacketBuilder;
import com.astraeus.core.net.channel.protocol.codec.game.ByteOrder;

/**
 * Represents the NPC Updating procedure; which loops through all the local npcs
 * and updates their masks according to their flags.
 * 
 * @author SeVen
 */
public class NpcUpdating {
	
	/**
	 * Updates an npc's movement queue.
	 * 
	 * @param npc
	 * 		The npc that will be updated.
	 * 
	 * @param builder
	 * 		The builder used to place data into a buffer.
	 */
	public static void updateMovement(Npc npc, PacketBuilder builder) {
		if (npc.getWalkingDirection() == -1) {			
			if(npc.getUpdateFlags().isUpdateRequired()) {
				builder.putBits(1, 1);
				builder.putBits(2, 0);
				builder.putBits(1, 0);
			} else {
				builder.putBits(1, 0);
			}			
		} else {
			builder.putBits(1, 1);
			builder.putBits(2, 1);
			builder.putBits(3, npc.getWalkingDirection());
			builder.putBits(1, 1);
		}
	}
	
	/**
	 * Displays an NPC on a players client.
	 * 
	 * @param npc
	 * 		The npc to display.
	 * 
	 * @param player
	 * 		The player to display the npc for.
	 * 
	 * @param builder
	 * 		The builder used to place data into a buffer.
	 * 
	 */
	public static void addNPC(Npc npc, Player player, PacketBuilder builder) {
		npc.getUpdateFlags().flag(UpdateFlag.REGISTERED_LOCALLY);
		builder.putBits(14,  npc.getIndex());
		builder.putBits(5,  npc.getPosition().getY() - player.getPosition().getY());
		builder.putBits(5, npc.getPosition().getX() - player.getPosition().getY());
		builder.putBits(1, 1); //movement queue?
		builder.putBits(12, npc.getId());
		builder.putBits(1, npc.getUpdateFlags().isUpdateRequired() ? 1 : 0);
	}
	
	/**
	 * Appends a mask update for an npc.
	 * 
	 * @param npc
	 * 		The npc that the update masks are for.
	 * 
	 * @param update
	 * 		The update buffer to place data in.
	 */
	public static void appendUpdates(Npc npc, PacketBuilder update) {
		
		int mask = 0x4;
		
		if (npc.getUpdateFlags().get(UpdateFlag.FACE_COORDINATE)) {
			mask |= 0x4;
		}
		
		update.putByte(mask);
		
		if (npc.getUpdateFlags().get(UpdateFlag.FACE_COORDINATE)) {
			final Position position = (Position) npc.getAttributes().get(Attributes.FACE_COORDINATE);
			update.putShort(position.getX() * 2 + 1, ByteOrder.LITTLE);
			update.putShort(position.getY() * 2 + 1, ByteOrder.LITTLE);
		}		
	}

}
