package main.astraeus.game.model.entity.object;

import java.util.LinkedHashSet;
import java.util.Set;

import main.astraeus.game.model.World;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.outgoing.impl.SendCreateObject;

public final class GameObjectManager {

	/**
	 * The singleton pattern implementation to prevent direct static
	 * access.
	 */
	private static GameObjectManager singleton;

	/**
	 * A collection of the globals objects in the virtual world.
	 */
	private final Set<GameObject> objects = new LinkedHashSet<GameObject>();

	/**
	 * Returns the singleton pattern implementation.
	 * 
	 * @return The returned pattern implementation.
	 */
	public static final GameObjectManager getSingleton() {

		if (singleton == null) {
			singleton = new GameObjectManager();
		}

		return singleton;
	}

	/**
	 * Creates a global object for all players within a 30 tile radius.
	 * 
	 * @param object The new object to be created.
	 */
	public final void createObject(GameObject object) {

		for (Player player : World.getPlayers()) {
			
			if (player == null || !player.isRegistered()) {
				continue;
			}

			if (player.getPosition().isWithinDistance(object.getPosition(), 30)) {
				player.send(new SendCreateObject(object));
			}
		}

		objects.add(object);
	}
	
	/**
	 * Updates the regional objects for a player upon region change.
	 * 
	 * @param player The player who's world is being updated.
	 */
	public static final void updateRegionalObjects(final Player player) {

		for (GameObject object : GameObjectManager.getSingleton().getObjects()) {

			if (player.getPosition().isWithinDistance(object.getPosition(), 50)) {
				player.send(new SendCreateObject(object));
			}
		}
	}

	/**
	 * Returns the global objects in the virtual world.
	 * 
	 * @return The returned global object collection.
	 */
	public final Set<GameObject> getObjects() {
		return objects;
	}
}
