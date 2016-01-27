package main.astraeus.content.dialogue;

import com.google.common.collect.ImmutableList;

import main.astraeus.game.model.entity.mobile.player.Player;

public abstract class Dialogue {

      /**
       * The action buttons responsible for dialogues.
       */
      public static final ImmutableList<Integer> DIALOGUE_BUTTONS =
                  ImmutableList.of(2461, 2471, 2482, 2462, 2472, 2483, 2473, 2484, 2485);

      /**
       * Checks if the button triggered is an optional dialogue button.
       *
       * @param button The index of the button being checked.
       *
       * @return The result of the operation.
       */
      public static final boolean isDialogueButton(int button) {
            return DIALOGUE_BUTTONS.stream().anyMatch(search -> DIALOGUE_BUTTONS.contains(button));
      }

      /**
       * Represents the progression of the dialogue.
       */
      private int stage = 0;

      /**
       * Sends a player a dialogue.
       * 
       * @param player The player to send dialogues.
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
       * @param stage The new stage to set.
       */
      public final void setDialogueStage(int stage) {
            this.stage = stage;
      }
}
