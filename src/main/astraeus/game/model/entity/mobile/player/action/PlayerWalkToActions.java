package main.astraeus.game.model.entity.mobile.player.action;

import main.astraeus.content.dialogue.Dialogues;
import main.astraeus.content.dialogue.impl.BankerDialogue;
import main.astraeus.game.model.Position;
import main.astraeus.game.model.entity.item.container.DepositContainer;
import main.astraeus.game.model.entity.mobile.player.Player;

public final class PlayerWalkToActions {

	public static final void objectFirstClick(final Player player, final Position position, final int index) {

		switch (index) {
		
		case 2213:
			Dialogues.sendDialogue(player, new BankerDialogue());
			break;
			
		case 9398:
			DepositContainer.openDepositBox(player);
			break;

		default:
			player.sendMessage("[First Click Object] - Unhandled click - ObjectId: " + index);
			break;

		}
	}
	
	public static final void objectSecondClick(final Player player, final Position position, final int index) {
		
		switch (index) {
		
		default:
			player.sendMessage("[Second Click Object] - Unhandled click: ObjectId: " + index);
			break;
			
		}
	}

}
