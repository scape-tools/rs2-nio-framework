package main.astraeus.content.dialogue;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.utility.Utilities;

/**
 * The static utility class that contains functions for sending dialogues.
 * 
 * @author SeVen <https://github.com/7winds>
 */
public class Dialogues {
	
	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(Dialogues.class.getName());

    /**
     * The maximum length of a single line of dialogue.
     */
    private static final int MAXIMUM_LENGTH = ("A string representing maximum dialogue text length!!").length();
	
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
	 * 
	 */
	public static void sendOption(Player player, String option1, String option2, DialogueOption option) {
		player.sendString("Select an Option", 2460);
		player.sendString(option1, 2505);
		player.sendString(option2, 2206);
		player.sendChatBoxInterface(2459);
		player.setDialogueOption(option);
	}
	
	public static void sendOption(Player player, String option1, String option2, String option3, DialogueOption option) {
		player.sendString("Select an Option", 2470);
		player.sendString(option1, 2215);
		player.sendString(option2, 2216);
		player.sendString(option3, 2217);
		player.sendChatBoxInterface(2469);
		player.setDialogueOption(option);
	}
	
	public static void sendOption(Player player, String option1, String option2, String option3, String option4, DialogueOption option) {
		player.sendString("Select an Option", 2481);
		player.sendString(option1, 2226);
		player.sendString(option2, 2227);
		player.sendString(option3, 2228);
		player.sendString(option4, 2229);
		player.sendChatBoxInterface(2480);
		player.setDialogueOption(option);
	}
	
	public static void sendOption(Player player, String option1, String option2, String option3, String option4, String option5, DialogueOption option) {
		player.sendString("Select an Option", 2493);
		player.sendString(option1, 2238);
		player.sendString(option2, 2239);
		player.sendString(option3, 2240);
		player.sendString(option4, 2241);
		player.sendString(option5, 2242);
		player.sendChatBoxInterface(2492);
		player.setDialogueOption(option);
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
		validateLength(lines);
		switch(lines.length) {		
		case 1:
			player.sendInterfaceAnimation(4883, expression.getId());
			player.sendString(npcName, 4884);
			player.sendString(lines[0], 4885);
			player.sendDialogueNpcHead(npcId, 4883);
			player.sendChatBoxInterface(4882);
			break;
			
		case 2:
			player.sendInterfaceAnimation(4888, expression.getId());
			player.sendString(npcName, 4889);
			player.sendString(lines[0], 4890);
			player.sendString(lines[1], 4891);
			player.sendDialogueNpcHead(npcId, 4888);
			player.sendChatBoxInterface(4887);
			break;
			
		case 3:
			player.sendInterfaceAnimation(4894, expression.getId());
			player.sendString(npcName, 4895);
			player.sendString(lines[0], 4896);
			player.sendString(lines[1], 4897);
			player.sendString(lines[2], 4898);
			player.sendDialogueNpcHead(npcId, 4894);
			player.sendChatBoxInterface(4893);
			break;
			
		case 4:
			player.sendInterfaceAnimation(4901, expression.getId());
			player.sendString(npcName, 4902);
			player.sendString(lines[0], 4903);
			player.sendString(lines[1], 4904);
			player.sendString(lines[2], 4905);
			player.sendString(lines[3], 4906);
			player.sendDialogueNpcHead(npcId, 4901);
			player.sendChatBoxInterface(4900);
			break;
			
		default:
			logger.log(Level.SEVERE, String.format("Invalid npc dialogue line length: %s", lines.length));
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
		validateLength(lines);
		switch(lines.length) {		
		case 1:
			player.sendInterfaceAnimation(969,  expression.getId());
			player.sendString(Utilities.capitalizePlayerName(player.getDetails().getUsername()), 714);
			player.sendString(lines[0], 715);
			player.sendDialoguePlayerHead(969);
			player.sendChatBoxInterface(968);
			break;
			
		case 2:
			player.sendInterfaceAnimation(974,  expression.getId());
			player.sendString(Utilities.capitalizePlayerName(player.getDetails().getUsername()), 719);
			player.sendString(lines[0], 720);
			player.sendString(lines[1], 721);
			player.sendDialoguePlayerHead(974);
			player.sendChatBoxInterface(973);
			break;
			
		case 3:
			player.sendInterfaceAnimation(980,  expression.getId());
			player.sendString(Utilities.capitalizePlayerName(player.getDetails().getUsername()), 725);
			player.sendString(lines[0], 726);
			player.sendString(lines[1], 727);
			player.sendString(lines[2], 728);
			player.sendDialoguePlayerHead(980);
			player.sendChatBoxInterface(979);
			break;
			
		case 4:
			player.sendInterfaceAnimation(987,  expression.getId());
			player.sendString(Utilities.capitalizePlayerName(player.getDetails().getUsername()), 988);
			player.sendString(lines[0], 989);
			player.sendString(lines[1], 990);
			player.sendString(lines[2], 991);
			player.sendString(lines[3], 992);
			player.sendDialoguePlayerHead(987);
			player.sendChatBoxInterface(986);
			break;
			
		default:
			logger.log(Level.SEVERE, String.format("Invalid player dialogue line length: %s", lines.length));
			break;			
		}		

	}
	
    /**
     * The method that validates the length of {@code text}.
     *
     * @param text
     *            the text that will be validated.
     * @throws IllegalStateException
     *             if any lines of the text exceed a certain length.
     */
    private static void validateLength(String... text) {
        if (Arrays.stream(text).filter(Objects::nonNull).anyMatch(s -> s.length() > MAXIMUM_LENGTH))
            throw new IllegalStateException("Dialogue length too long, maximum length is: " + MAXIMUM_LENGTH);
    }

}
