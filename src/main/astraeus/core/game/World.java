package main.astraeus.core.game;

import main.astraeus.core.game.model.entity.mobile.MobileEntity;
import main.astraeus.core.game.model.entity.mobile.npc.Npc;
import main.astraeus.core.game.model.entity.mobile.player.Player;

public class World {
	
	private static final Player[] players = new Player[2000];
	
	private static final Npc[] npcs = new Npc[3000];
	
	/**
	 * Registers and adds a player into the game world.
	 * 
	 * @param Player
	 *            The player to add.
	 */
	public static void registerPlayer(Player player) {
		int slot = -1;

		for (int index = 1; index < World.getPlayers().length; index++) {
			if ((World.getPlayers()[index] == null)) {
				slot = index;
				break;
			}
		}
		player.setId(-1);
		player.setSlot(slot);
		World.getPlayers()[slot] = player;
		World.getPlayers()[slot].setRegistered(true);
	}
	
	/**
	 * Deregisters a {@link MobileEntity} from the game world.
	 * 
	 * @param entity
	 *            The entity to remove.
	 */
	public static void deregister(MobileEntity entity) {

		if (entity == null || !entity.isRegistered()) {
			return;
		}

		if (entity.isPlayer()) {
			World.getPlayers()[entity.getSlot()].setRegistered(false);
			World.getPlayers()[entity.getSlot()] = null;
		} else if (entity.isNpc()) {
			World.getNpcs()[entity.getSlot()].setRegistered(false);
			World.getNpcs()[entity.getSlot()] = null;
		} else {
			System.out.println("Unknown entity type while deregistering: ");
		}

	}

	/**
	 * Checks if a player with a specific name is registered in the virtual world.
	 * 
	 * @param accountName The name of the player to check.
	 * 
	 * @return Whether the player is registered in the virtual world or not.
	 */
	public static final boolean isLoggedIn(String accountName) {		
		for (Player player : World.getPlayers()) {			
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

	/**
	 * @return the players
	 */
	public static Player[] getPlayers() {
		return players;
	}	

}
