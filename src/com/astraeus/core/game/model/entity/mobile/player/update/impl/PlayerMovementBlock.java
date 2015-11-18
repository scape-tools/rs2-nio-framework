package com.astraeus.core.game.model.entity.mobile.player.update.impl;

import com.astraeus.core.game.model.entity.UpdateFlags;
import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.model.entity.mobile.player.update.UpdateBlock;
import com.astraeus.core.net.channel.message.PacketBuilder;

public final class PlayerMovementBlock extends UpdateBlock {

	@Override
	public void update(Player player, PacketBuilder buffer) {
		System.out.println("Called");
		if (player.getUpdateFlags().contains(UpdateFlags.UPDATE_MAP_REGION)) {
			buffer.putBits(1, 1);
			buffer.putBits(2, 3);
			buffer.putBits(2, player.getPosition().getZ());
			buffer.putBits(1, 1);
			buffer.putBits(1, player.updateRequired() ? 1 : 0);
			buffer.putBits(7, player.getPosition().getLocalY(player.getLastPosition()));
			buffer.putBits(7, player.getPosition().getLocalX(player.getLastPosition()));
		} else {
			if (player.getWalkingDirection() == -1) {
				if (player.updateRequired()) {
					buffer.putBits(1, 1);
					buffer.putBits(2, 0);
				} else {
					buffer.putBits(1, 0);
				}
			} else {
				if (player.getRunningDirection() == -1) {
					buffer.putBits(1, 1);
					buffer.putBits(2, 1);
					buffer.putBits(3, player.getWalkingDirection());
					buffer.putBits(1, player.updateRequired() ? 1 : 0);
				} else {
					buffer.putBits(1, 1);
					buffer.putBits(2, 2);
					buffer.putBits(3, player.getWalkingDirection());
					buffer.putBits(3, player.getRunningDirection());
					buffer.putBits(1, player.updateRequired() ? 1 : 0);
				}
			}
		}
	}
}