package com.astraeus.core.game;

import com.astraeus.core.Server;
import com.astraeus.core.game.model.entity.EntityList;
import com.astraeus.core.game.model.entity.mobile.npc.Npc;
import com.astraeus.core.game.model.entity.mobile.player.Player;

public class World {
	
	private static EntityList<Npc> npcList = new EntityList<>(2000);

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
	 * @return the npcList
	 */
	public static EntityList<Npc> getNpcList() {
		return npcList;
	}
}
