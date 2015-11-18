package com.astraeus.core.game.model.entity.mobile.player.action;

import com.astraeus.core.game.model.entity.Position;
import com.astraeus.core.game.model.entity.mobile.player.Player;

public final class PlayerWalkToActions {

	public static final void objectFirstClick(final Player player, final Position position, final int index) {

		switch (index) {

		default:
			player.getPacketSender().sendMessage("[First Click Object] - Unhandled click - ObjectId: " + index);
			break;

		}
	}
	
	public static final void objectSecondClick(final Player player, final Position position, final int index) {
		
		switch (index) {
		
		default:
			player.getPacketSender().sendMessage("[Second Click Object] - Unhandled click: ObjectId: " + index);
			break;
			
		}
	}

}
