package main.astraeus.content.dialogue.impl;

import main.astraeus.content.dialogue.Dialogue;
import main.astraeus.content.dialogue.DialogueOption;
import main.astraeus.content.dialogue.Dialogues;
import main.astraeus.game.model.entity.mobile.player.Player;

public class TestDialogue extends Dialogue {

	@Override
	public void sendDialogues(Player player) {
		switch(getDialogueStage()) {
		case 0:
			Dialogues.sendOption(player, "This is option 1", "This is option 2", "test 3", "test 4", "Test 5", new DialogueOption() {

				@Override
				public boolean handleSelection(Player player, int buttonId) {
					return false;
				}
				
			});
			Dialogues.endDialogue(player);
			break;
		}
	}

}
