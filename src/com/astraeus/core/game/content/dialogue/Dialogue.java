package com.astraeus.core.game.content.dialogue;

import com.astraeus.core.game.model.entity.mobile.player.Player;

public abstract class Dialogue {

	/**
	 * Represents the progression of the dialogue.
	 */
	private int stage = 0;

	/**
	 * Sends a player a dialogue.
	 * 
	 * @param player
	 * 		The player to send dialogues.
	 */
	public abstract void sendDialogues(Player player);

	/**
	 * Gets the current dialogue stage.
	 * 
	 * @return stage;
	 */
	public final int getDialogueStage() {
		return stage;
	}
	
	/**
	 * Sets dialogue to a new stage.
	 * 
	 * @param stage
	 * 		The new stage to set.
	 */
	public final void setDialogueStage(int stage) {
		this.stage = stage;
	}
}
