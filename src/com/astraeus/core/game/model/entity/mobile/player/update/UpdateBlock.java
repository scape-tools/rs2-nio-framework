package com.astraeus.core.game.model.entity.mobile.player.update;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.PacketBuilder;

public abstract class UpdateBlock {

	/**
	 * Executes an individual block of the updating protocol.
	 * 
	 * @param player
	 *		The player to update.
	 * 
	 * @param buffer
	 * 		The internal buffer responsible for pooling the update's data.
	 */
	public abstract void update(Player player, PacketBuilder buffer);
}