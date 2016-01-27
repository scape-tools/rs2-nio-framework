package main.astraeus.content.dialogue.impl;

import main.astraeus.content.dialogue.Dialogue;
import main.astraeus.content.dialogue.DialogueOption;
import main.astraeus.content.dialogue.Dialogues;
import main.astraeus.content.dialogue.Expression;
import main.astraeus.game.model.entity.mobile.player.Player;

public final class BankerDialogue extends Dialogue {

	@Override
	public void sendDialogues(Player player) {

		switch (getDialogueStage()) {

		case 0:
			Dialogues.sendNpcChat(player, "Banker", 494, Expression.HAPPY, "Good day. How may I help you?");
			break;
			
		case 1:
			Dialogues.sendOption(player, "I'd like to access my bank account, please.", "I'd like to check my PIN settings.", "I don't need anything.", new DialogueOption() {

				@Override
				public boolean handleSelection(Player player, OptionType option) {

					switch(option) {
					case FIRST_OPTION:
						Dialogues.sendDialogue(player, new Dialogue() {

							@Override
							public void sendDialogues(Player player) {
								switch(getDialogueStage()) {
								
								case 0:
									Dialogues.sendPlayerChat(player, Expression.CALM, "I'd like to access my bank account, please.");
									Dialogues.endDialogue(player);
									break;
								}
							}
							
						});
						break;
					case SECOND_OPTION:						
						Dialogues.sendDialogue(player, new Dialogue() {

							@Override
							public void sendDialogues(Player player) {
							
								switch(getDialogueStage()) {
								case 0:
									Dialogues.sendPlayerChat(player, Expression.CALM, "I'd like to check my PIN settings.");
									break;
									
								case 1:
									Dialogues.sendNpcChat(player, "Banker", 494, Expression.CALM, "Sorry, this feature is currently unavailable.");
									Dialogues.endDialogue(player);
									break;
								}
							}
							
						});						
						break;
					case THIRD_OPTION:
						Dialogues.sendDialogue(player, new Dialogue() {

							@Override
							public void sendDialogues(Player player) {
								switch(getDialogueStage()) {
								case 0:
									Dialogues.sendPlayerChat(player, Expression.CALM, "I don't need anything.");
									Dialogues.endDialogue(player);
									break;
								}
							}							
						});
						break;
                                    default:
                                          break;		
					}			
					
					return false;
				}
				
			});
			break;
		}
	}
}
