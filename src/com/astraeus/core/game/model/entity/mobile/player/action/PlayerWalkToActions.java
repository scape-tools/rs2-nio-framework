package com.astraeus.core.game.model.entity.mobile.player.action;

import com.astraeus.core.game.model.entity.Position;
import com.astraeus.core.game.model.entity.mobile.player.Player;

public final class PlayerWalkToActions {

	public static final void objectFirstClick(final Player player, final Position point, final int index) {

		switch (index) {

		default:
			player.getPacketSender().sendMessage("Clicked object: " + index);
			break;

		}
	}

}
