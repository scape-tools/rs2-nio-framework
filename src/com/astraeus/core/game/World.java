package com.astraeus.core.game;

import com.astraeus.core.Server;
import com.astraeus.core.game.model.entity.mobile.player.Player;

public class World {
	
	/**
	 * The class instance to avoid direct static access upon reference to this
	 * class file.
	 */
	private static final World singleton = new World();
	
	/**
	 * Checks if a player with a specific name is registered in the virtual world.
	 * 
	 * @param accountName The name of the player to check.
	 * 
	 * @return Whether the player is registered in the virtual world or not.
	 */
	public final boolean isLoggedIn(String accountName) {
		
		for (Player player : Server.getUpdateProcessor().getPlayers().values()) {
			
			if (player != null) {

				if (player.getUsername().equalsIgnoreCase(accountName)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns the encapsulated instance of this class.
	 * 
	 * @return The returned instance.
	 */
	public final static World getSingleton() {
		return singleton;
	}

}
