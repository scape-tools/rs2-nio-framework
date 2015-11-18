package com.runescape.core.game.model.entity.mobile.player;

import java.util.logging.Logger;

import com.runescape.core.Server;
import com.runescape.core.game.model.entity.EntityEventListener;
import com.runescape.core.game.model.entity.UpdateFlags;

public final class PlayerEventListener extends EntityEventListener<Player> {

	@Override
	public void add(Player player) {

			/*
			 * Adds the player to the registry.
			 */
			Server.getUpdateProcessor().addPlayer(player);

			/*
			 * Sets an initial starting location.
			 */
			player.setLocation(PlayerConstants.START_COORDINATES);

			/*
			 * Updates the player's region upon initial placement.
			 */
			player.getUpdateFlags().add(UpdateFlags.UPDATE_MAP_REGION);

			/*
			 * Updates the player's appearance.
			 */
			player.getUpdateFlags().add(UpdateFlags.UPDATE_APPEARANCE);

			/*
			 * Welcome message.
			 */
			player.getPacketSender().sendMessage(PlayerConstants.WELCOME_MESSAGE);
			
			player.getPacketSender().sendTabs();

			Logger.getLogger(PlayerEventListener.class.getCanonicalName()).info("Completed Login Block : " + player.toString() + ".");

	}

	@Override
	public void remove(Player player) {

		/*
		 * Removes the player from the processor's registry.
		 */
		Server.getUpdateProcessor().removePlayer(player);
	}

	@Override
	public void update(Player player) {

			/*
			 * Dispenses the updating packet.
			 */
			player.getPacketSender().sendPlayerUpdate();
	}
}