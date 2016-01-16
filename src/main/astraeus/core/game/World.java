package main.astraeus.core.game;

import main.astraeus.core.Server;
import main.astraeus.core.game.model.entity.mobile.npc.Npc;
import main.astraeus.core.game.model.entity.mobile.player.Player;

public class World {
	
	private static final Npc[] npcs = new Npc[3000];

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

	/**
	 * @return the npcs
	 */
	public static Npc[] getNpcs() {
		return npcs;
	}	

}
