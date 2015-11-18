package com.astraeus.core.game.content.dialogue.impl;

import com.astraeus.core.game.content.dialogue.Dialogue;
import com.astraeus.core.game.content.dialogue.DialogueContainer;
import com.astraeus.core.game.content.dialogue.Expression;
import com.astraeus.core.game.model.entity.mobile.player.Player;

public final class BankerDialogue extends Dialogue {

	@Override
	public void sendDialogues(Player player) {

		switch (getDialogueStage()) {

		case 0:
			DialogueContainer.sendNpcChat(player, "Banker", 494, Expression.HAPPY, "Good day. How may I help you?");
			break;
			
		}
	}
}
