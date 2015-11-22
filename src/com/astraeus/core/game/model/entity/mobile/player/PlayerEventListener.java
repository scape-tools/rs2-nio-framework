package com.astraeus.core.game.model.entity.mobile.player;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.astraeus.core.Server;
import com.astraeus.core.game.model.entity.EntityEventListener;
import com.astraeus.core.game.model.entity.UpdateFlags;
import com.astraeus.core.game.model.entity.mobile.player.Player.Attributes;
import com.astraeus.core.game.pulse.PulseScheduler;

public final class PlayerEventListener extends EntityEventListener<Player> {

	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(PlayerEventListener.class.getName());
	
	@Override
	public void add(Player player) {

			/*
			 * Adds the player to the registry.
			 */
			Server.getUpdateProcessor().addPlayer(player);
			
			player.setPosition(player.load() ? player.getPosition() : PlayerConstants.START_COORDINATES);
			
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
			
			logger.log(Level.INFO, String.format("[%s] has successfully logged in.", player.toString()));

	}

	@Override
	public void remove(Player player) {
		
		player.getAttributes().put(Attributes.WALK_TO_ACTION, false);
		
		player.getMovement().resetMovement();

		player.save();
		
		/*
		 * Removes the player from the processor's registry.
		 */
		Server.getUpdateProcessor().removePlayer(player);
		
		PulseScheduler.getInstance().destoryPulsesForOwner(player.getDetails().getUsername());
		
		player.getPacketSender().sendLogout();
		
		logger.log(Level.INFO, String.format("[%s] has left the server.", player.toString()));
	}

	@Override
	public void update(Player player) {

			/*
			 * Dispenses the updating packet.
			 */
			player.getPacketSender().sendPlayerUpdate();
	}
}