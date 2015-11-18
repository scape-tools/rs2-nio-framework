package com.runescape.core.game.model.entity.mobile.player.update;

import com.runescape.core.game.model.entity.mobile.player.Player;
import com.runescape.core.net.channel.message.PacketBuilder;

public abstract class UpdateBlock {

	/**
	 * Executes an individual block of the updating protocol.
	 * 
	 * @param player The target of the procedure.
	 * 
	 * @param buffer The internal buffer responsible for pooling the update's data.
	 */
	public abstract void update(Player player, PacketBuilder buffer);
}