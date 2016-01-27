package main.astraeus.game.model.entity.mobile.player.event;

import java.io.File;

import main.astraeus.Configuration;
import main.astraeus.game.model.entity.mobile.player.Player;

/**
 * @author Dylan Vicchiarelli
 *
 *         An event which handles an action of the player's account file.
 */
public abstract class PlayerFileEvent {

	/**
	 * The owner of the file.
	 */
	private final Player player;

	/**
	 * The path to the file directory.
	 */
	private final File file;

	/**
	 * The default class constructor.
	 * 
	 * @param player
	 *            The owner of the file.
	 */
	public PlayerFileEvent(Player player) {
		this.player = player;
		this.file = new File(Configuration.DATA + "characters/" + getPlayer().getDetails().getUsername() + ".json");
	}

	/**
	 * Returns the owner of the file.
	 * 
	 * @return The returned owner.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Returns an encapsulated instance of the player's file path.
	 * 
	 * @return The returned instance.
	 */
	public File getFile() {
		return file;
	}
}
