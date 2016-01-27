package main.astraeus.content.clicking;

import main.astraeus.content.dialogue.Dialogue;
import main.astraeus.content.dialogue.DialogueOption.OptionType;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.player.PlayerRights;
import main.astraeus.net.packet.outgoing.impl.SendLogout;
import main.astraeus.net.packet.outgoing.impl.SendMessage;

public class ButtonClick {

      /**
       * Handles the button click for a player.
       * 
       * @param player The player clicking the button
       * 
       * @param button The id of the button being clicked.
       */
      public static void handleAction(Player player, int button) {

            if (player.getRights().greaterOrEqual(PlayerRights.DEVELOPER) && player.isDebugMode()) {
                  player.send(new SendMessage(String.format("@whi@[ClickButtons] - %d", button)));
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

            }

            /* Dialogue */
            OptionType option = OptionType.FIRST_OPTION;

            if (Dialogue.isDialogueButton(button) && player.getDialogueOption() != null) {
                  switch (button) {

                        case 2461:
                        case 2471:
                        case 2482:
                              option = OptionType.FIRST_OPTION;
                              player.getDialogueOption().handleSelection(player, option);
                              break;

                        case 2462:
                        case 2472:
                        case 2483:
                              option = OptionType.SECOND_OPTION;
                              player.getDialogueOption().handleSelection(player, option);
                              break;

                        case 2473:
                        case 2484:
                              option = OptionType.THIRD_OPTION;
                              player.getDialogueOption().handleSelection(player, option);
                              break;

                        case 2485:
                              option = OptionType.FOURTH_OPTION;
                              player.getDialogueOption().handleSelection(player, option);
                              break;

                  }
            }

      }

}
