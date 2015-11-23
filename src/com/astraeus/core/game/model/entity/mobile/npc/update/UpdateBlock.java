package com.astraeus.core.game.model.entity.mobile.npc.update;

import com.astraeus.core.game.model.entity.mobile.npc.Npc;
import com.astraeus.core.net.channel.packet.PacketBuilder;

public abstract class UpdateBlock {

	/**
	 * Executes an individual block of the updating protocol.
	 * 
	 * @param npc
	 * 		The npc to update.
	 * 
	 * @param buffer
	 * 		The internal buffer responsible for pooling the update's data.
	 */
	public abstract void update(Npc npc, PacketBuilder builder);
}