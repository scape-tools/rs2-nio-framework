package com.astraeus.core.game.content.dialogue;

import com.astraeus.core.game.model.entity.mobile.player.Player;

public abstract class DialogueOption {

	/**
	 * Handles a players option dialogue.
	 * 
	 * @param player
	 * 		The player to handle this option dialogue for.
	 * 
	 * @param button
	 * 		The button to click on the option dialogue.
	 * 
	 * @return {@code true} if this can be performed, {@code false} otherwise.
	 */
	public abstract boolean handleSelection(Player player, int button);
}
