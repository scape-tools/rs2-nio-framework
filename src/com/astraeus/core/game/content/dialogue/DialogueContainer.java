package com.astraeus.core.game.content.dialogue;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.utility.Utilities;

/**
 * The static utility class that holds methods for sending various dialogues.
 * 
 * @author SeVen
 */
public class DialogueContainer {
	
	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(DialogueContainer.class.getName());

	/**
	 * Sends a player a dialogue.
	 * 
	 * @param player
	 * 		The player that is receiving the dialogue.
	 * @param dialogue
	 * 		The dialogue to sent.
	 */
	public static final void sendDialogue(Player player, Dialogue dialogue) {
		player.setDialogue(dialogue);
		dialogue.sendDialogues(player);
	}

	/**
	 * Ends a players existing dialogue.
	 * 
	 * @param player
	 * 		The player that will be ending the dialogue.
	 */
	public static final void endDialogue(Player player) {
		player.getDialogue().setDialogueStage(-1);
	}

	/**
	 * Sends a dialogue option.
	 * 
	 * @param player
	 * 		The player that is receiving the dialogue option.
	 * 
	 * @param option
	 * 		The option dialogue.
	 * 
	 * @param lines
	 * 		The lines in the option dialogue.
	 */
	public static final void sendOption(Player player, DialogueOption option, String... lines) {

		switch (lines.length) {
		case 0:
			player.getPacketSender().sendString("Select an Option", 2460);
			player.getPacketSender().sendString(lines[0], 2461);
			player.getPacketSender().sendString(lines[1], 2462);
			player.getPacketSender().sendChatInterface(2459);
			player.setDialogueOption(option);
			break;

		case 1:
			player.getPacketSender().sendString("Select an Option", 2470);
			player.getPacketSender().sendString(lines[0], 2471);
			player.getPacketSender().sendString(lines[1], 2472);
			player.getPacketSender().sendString(lines[2], 2472);
			player.getPacketSender().sendChatInterface(2469);
			player.setDialogueOption(option);
			break;

		case 2:
			player.getPacketSender().sendString("Select an Option", 2481);
			player.getPacketSender().sendString(lines[0], 2482);
			player.getPacketSender().sendString(lines[1], 2483);
			player.getPacketSender().sendString(lines[2], 2484);
			player.getPacketSender().sendString(lines[3], 2485);
			player.getPacketSender().sendChatInterface(2480);
			player.setDialogueOption(option);
			break;

		case 3:
			player.getPacketSender().sendString("Select an Option", 2493);
			player.getPacketSender().sendString(lines[0], 2494);
			player.getPacketSender().sendString(lines[1], 2495);
			player.getPacketSender().sendString(lines[2], 2496);
			player.getPacketSender().sendString(lines[3], 2497);
			player.getPacketSender().sendString(lines[4], 2498);
			player.getPacketSender().sendChatInterface(2492);
			player.setDialogueOption(option);
			break;

		default:
			logger.log(Level.SEVERE, String.format("Invalid dialogue option line length: %s", lines.length));
			break;

		}

	}
	
	/**
	 * Sends a dialogue with a npc talking.
	 * 
	 * @param player
	 * 		The player that is receiving the dialogue.
	 * 
	 * @param npcName
	 * 		The name of the npc.
	 * 
	 * @param npcId
	 * 		The id of the npc.
	 * 
	 * @param expression
	 * 		The npc's expression.
	 * 
	 * @param lines
	 * 		The lines of dialogue.
	 */
	public static final void sendNpcChat(Player player, String npcName, int npcId, Expression expression, String... lines) {
		
		int line = lines.length - 1;
		
		switch(line) {
		
		case 0:
			player.getPacketSender().sendDialogueAnimation(4883, expression.getId());
			player.getPacketSender().sendString(npcName, 4884);
			player.getPacketSender().sendString(lines[0], 4885);
			player.getPacketSender().sendDialogueNpcHead(npcId, 4883);
			player.getPacketSender().sendChatInterface(4882);
			break;
			
		case 1:
			player.getPacketSender().sendDialogueAnimation(4888, expression.getId());
			player.getPacketSender().sendString(npcName, 4889);
			player.getPacketSender().sendString(lines[0], 4890);
			player.getPacketSender().sendString(lines[1], 4891);
			player.getPacketSender().sendDialogueNpcHead(npcId, 4888);
			player.getPacketSender().sendChatInterface(4887);
			break;
			
		case 2:
			player.getPacketSender().sendDialogueAnimation(4894, expression.getId());
			player.getPacketSender().sendString(npcName, 4895);
			player.getPacketSender().sendString(lines[0], 4896);
			player.getPacketSender().sendString(lines[1], 4897);
			player.getPacketSender().sendString(lines[2], 4898);
			player.getPacketSender().sendDialogueNpcHead(npcId, 4894);
			player.getPacketSender().sendChatInterface(4893);
			break;
			
		case 3:
			player.getPacketSender().sendDialogueAnimation(4901, expression.getId());
			player.getPacketSender().sendString(npcName, 4902);
			player.getPacketSender().sendString(lines[0], 4903);
			player.getPacketSender().sendString(lines[1], 4904);
			player.getPacketSender().sendString(lines[2], 4905);
			player.getPacketSender().sendString(lines[3], 4906);
			player.getPacketSender().sendDialogueNpcHead(npcId, 4901);
			player.getPacketSender().sendChatInterface(4900);
			break;
			
		default:
			logger.log(Level.SEVERE, String.format("Invalid npc dialogue line length: %s", line));
			break;
		
		}
		
	}
	
	/**
	 * Sends a dialogue with a player talking.
	 * 
	 * @param player
	 * 		The player that is talking.
	 * 
	 * @param expression
	 * 		The expression of this player.
	 * 
	 * @param lines
	 * 		The lines in this dialogue.
	 */
	public static void sendPlayerChat(Player player, Expression expression, String... lines) {
		
		int line = lines.length - 1;	
		
		switch(line) {		
		case 0:
			player.getPacketSender().sendDialogueAnimation(969,  expression.getId());
			player.getPacketSender().sendString(Utilities.capitalizePlayerName(player.getDetails().getUsername()), 970);
			player.getPacketSender().sendString(lines[0], 971);
			player.getPacketSender().sendDialoguePlayerHead(969);
			player.getPacketSender().sendChatInterface(976);
			break;
			
		case 1:
			player.getPacketSender().sendDialogueAnimation(974,  expression.getId());
			player.getPacketSender().sendString(Utilities.capitalizePlayerName(player.getDetails().getUsername()), 975);
			player.getPacketSender().sendString(lines[0], 976);
			player.getPacketSender().sendString(lines[1], 977);
			player.getPacketSender().sendDialoguePlayerHead(974);
			player.getPacketSender().sendChatInterface(973);
			break;
			
		case 2:
			player.getPacketSender().sendDialogueAnimation(980,  expression.getId());
			player.getPacketSender().sendString(Utilities.capitalizePlayerName(player.getDetails().getUsername()), 981);
			player.getPacketSender().sendString(lines[0], 982);
			player.getPacketSender().sendString(lines[1], 983);
			player.getPacketSender().sendString(lines[2], 984);
			player.getPacketSender().sendDialoguePlayerHead(980);
			player.getPacketSender().sendChatInterface(979);
			break;
			
		case 3:
			player.getPacketSender().sendDialogueAnimation(987,  expression.getId());
			player.getPacketSender().sendString(Utilities.capitalizePlayerName(player.getDetails().getUsername()), 988);
			player.getPacketSender().sendString(lines[0], 989);
			player.getPacketSender().sendString(lines[1], 990);
			player.getPacketSender().sendString(lines[2], 991);
			player.getPacketSender().sendString(lines[2], 992);
			player.getPacketSender().sendDialoguePlayerHead(987);
			player.getPacketSender().sendChatInterface(986);
			break;
			
		default:
			logger.log(Level.SEVERE, String.format("Invalid player dialogue line length: %s", line));
			break;			
			
		}		

	}	

}
