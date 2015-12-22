package main.astraeus.content.dialogue;

import main.astraeus.core.game.model.entity.mobile.player.Player;

public abstract class DialogueOption {

	public static final int FIRST_OPTION = 2471;

	public static final int SECOND_OPTION = 2472;

	public static final int THIRD_OPTION = 2473;

	/**
	 * Handles a players option dialogue.
	 * 
	 * @param player
	 *            The player to handle this option dialogue for.
	 * 
	 * @param button
	 *            The button to click on the option dialogue.
	 * 
	 * @return {@code true} if this can be performed, {@code false} otherwise.
	 */
	public abstract boolean handleSelection(Player player, int buttonId);
}
