package main.astraeus.core.net.packet.incoming.impl;

import java.util.ArrayList;
import java.util.Arrays;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.PlayerRights;
import main.astraeus.core.net.packet.incoming.IncomingPacket;
import main.astraeus.core.net.packet.incoming.IncomingPacketListener;
import main.astraeus.core.net.packet.incoming.IncomingPacketOpcode;
import main.astraeus.core.net.packet.outgoing.impl.SendLogout;

/**
 * The packet responsible for clicking in-game buttons.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode( 185 )
public class ButtonClickPacketListener implements IncomingPacketListener {
	
	/**
	 * The action button indexes of optional selections on a dialogue interface.
	 */
	public static final ArrayList<Integer> DIALOGUE_BUTTONS = new ArrayList<Integer>(Arrays.asList(2461, 2462, 2471, 2472, 2473));

	/**
	 * Checks if the button triggered is an optional dialogue button.
	 * 
	 * @param button The index of the button being checked.
	 * 
	 * @return The result of the operation.
	 */
	public final boolean isDialogueButton(int button) {
		return DIALOGUE_BUTTONS.contains(button);
	}
	
	@Override
	public void handleMessage(Player player, IncomingPacket packet) {
		final int button = packet.getBuffer().getShort();

		if (isDialogueButton(button) && player.getDialogueOption() != null && player.getDialogueOption().handleSelection(player, button)); {

		}
		
		if (player.getRights().greaterOrEqual(PlayerRights.DEVELOPER) && player.isServerDebug()) {
			player.sendMessage("[ButtonClick] - ButtonId: " + button);
		}
		
		switch (button) {

		// walk
		case 152:
			player.getMovement().setRunning(false);
			player.getMovement().setRunningQueueEnabled(false);
			break;

		// run
		case 153:
			player.getMovement().setRunning(true);
			player.getMovement().setRunningQueueEnabled(true);
			break;

		// logout
		case 2458:
			player.send(new SendLogout());
			break;

		default:
			if (player.getRights().greaterOrEqual(PlayerRights.DEVELOPER) && player.isServerDebug()) {
			player.sendMessage("[ButtonClick] - Unhandled ButtonId: " + button);
			}
			break;

		}
	}

}
