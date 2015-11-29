package com.astraeus.core.game.content.dialogue.impl;

import com.astraeus.core.game.content.dialogue.Dialogue;
import com.astraeus.core.game.content.dialogue.Dialogues;
import com.astraeus.core.game.content.dialogue.Expression;
import com.astraeus.core.game.model.entity.mobile.player.Player;

public final class BankerDialogue extends Dialogue {

	@Override
	public void sendDialogues(Player player) {

		switch (getDialogueStage()) {

		case 0:
			Dialogues.sendNpcChat(player, "Banker", 494, Expression.HAPPY, "Npc dialogue works.");
			break;
			
		case 1:
			Dialogues.sendNpcChat(player, "Banker", 494, Expression.HAPPY, "But player dialogue does not.", "Lets find out...");
			break;

		case 2:
			Dialogues.sendPlayerChat(player, Expression.CALM, "Hello there!");
			break;
			
		case 3:
			Dialogues.sendPlayerChat(player, Expression.CALM, "Helloooo!", "Helloooo");
			break;
			
		case 4:
			Dialogues.sendPlayerChat(player, Expression.CALM, "Testing..", "Test two", "Test three");
			Dialogues.endDialogue(player);
			break;

		}
	}
}
