package main.astraeus.content.dialogue;

import main.astraeus.game.model.entity.mobile.player.Player;

/**
 * The class responsible for options within dialogues.
 * 
 * @author SeVen
 */
public abstract class DialogueOption {

  /**
   * The type of dialogue option.
   */
  public enum OptionType {
    /**
     * The first line of a dialogue option.
     */
    FIRST_OPTION,

    /**
     * The second line of a dialogue option.
     */
    SECOND_OPTION,

    /**
     * The first line of a dialogue option.
     */
    THIRD_OPTION,

    /**
     * The fourth line of a dialogue option.
     */
    FOURTH_OPTION,

    /**
     * The fifth line of a dialogue option.
     */
    FIFTH_OPTION
  }

  /**
   * Handles a players option dialogue.
   * 
   * @param player
   *            The player to handle this option dialogue for.
   * 
   * @param button
   *            The button to click on the option dialogue.
   * 
   * @return {@code true} if this can be performed, {@code false} otherwise.
   */
  public abstract boolean handleSelection(Player player, OptionType option);
}
