package main.astraeus.core.game.model.entity.mobile.player;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.astraeus.core.Server;
import main.astraeus.core.game.model.entity.EntityEventListener;
import main.astraeus.core.game.model.entity.mobile.player.Player.Attributes;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.game.pulse.PulseScheduler;
import main.astraeus.core.net.channel.packet.outgoing.ChatBoxMessagePacket;
import main.astraeus.core.net.channel.packet.outgoing.LogoutPacket;
import main.astraeus.core.net.channel.packet.outgoing.PlayerUpdatePacket;

public final class PlayerEventListener extends EntityEventListener<Player> {

	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(PlayerEventListener.class.getName());
	
	@Override
	public void add(Player player) {
			Server.getUpdateProcessor().addPlayer(player);	
			
			player.setPosition(player.load() ? player.getPosition() : PlayerConstants.START_COORDINATES);
			player.getUpdateFlags().flag(UpdateFlag.REGION_CHANGING);
			player.getUpdateFlags().flag(UpdateFlag.APPEARANCE);
			player.write(new ChatBoxMessagePacket(PlayerConstants.WELCOME_MESSAGE));
			player.sendTabs();			
			logger.log(Level.INFO, String.format("[%s] has successfully logged in.", player.toString()));
	}

	@Override
	public void remove(Player player) {		
		player.getAttributes().put(Attributes.WALK_TO_ACTION, false);		
		player.getMovement().resetMovement();
		player.save();
		
		PulseScheduler.getInstance().destoryPulsesForOwner(player.getDetails().getUsername());		
		/*
		 * Removes the player from the processor's registry.
		 */
		Server.getUpdateProcessor().removePlayer(player);
		player.write(new LogoutPacket());		
		logger.log(Level.INFO, String.format("[%s] has left the server.", player.toString()));
	}

	@Override
	public void update(Player player) {
		
			player.write(new PlayerUpdatePacket());			
			//player.write(new NpcUpdatePacket());
	}
}