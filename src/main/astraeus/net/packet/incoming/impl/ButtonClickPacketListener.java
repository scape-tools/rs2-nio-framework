package main.astraeus.net.packet.incoming.impl;

import java.util.ArrayList;
import java.util.Arrays;

import main.astraeus.content.clicking.ButtonClick;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.PacketReader;
import main.astraeus.net.packet.incoming.IncomingPacket;
import main.astraeus.net.packet.incoming.IncomingPacketListener;
import main.astraeus.net.packet.incoming.IncomingPacketOpcode;

/**
 * The packet responsible for clicking in-game buttons.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode(185)
public class ButtonClickPacketListener implements IncomingPacketListener {

      /**
       * The action button indexes of optional selections on a dialogue interface.
       */
      public static final ArrayList<Integer> DIALOGUE_BUTTONS =
                  new ArrayList<Integer>(Arrays.asList(2461, 2462, 2471, 2472, 2473));

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

            final PacketReader reader = packet.getReader();

            final int button = reader.readShort();
            
            ButtonClick.handleAction(player, button);
      }

}
