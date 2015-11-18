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
	 * @return Returns true, if this can be performed, false if it can't.
	 */
	public abstract boolean handle(Player player, int button);
}
