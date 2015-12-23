package main.astraeus.core.game;

import main.astraeus.core.Server;
import main.astraeus.core.game.model.entity.mobile.player.Player;

public class World {

	/**
	 * Checks if a player with a specific name is registered in the virtual world.
	 * 
	 * @param accountName The name of the player to check.
	 * 
	 * @return Whether the player is registered in the virtual world or not.
	 */
	public static final boolean isLoggedIn(String accountName) {		
		for (Player player : Server.getUpdateProcessor().getPlayers().values()) {			
			if (player != null) {
				if (player.getUsername().equalsIgnoreCase(accountName)) {
					return true;
				}
			}
		}
		return false;
	}

}
