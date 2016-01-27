package main.astraeus.core.net.packet.incoming.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.astraeus.core.game.model.entity.item.Item;
import main.astraeus.core.game.model.entity.item.ItemDefinition;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.PacketReader;
import main.astraeus.core.net.packet.incoming.IncomingPacket;
import main.astraeus.core.net.packet.incoming.IncomingPacketListener;
import main.astraeus.core.net.packet.incoming.IncomingPacketOpcode;
import main.astraeus.core.net.packet.outgoing.impl.SendDisplayInterface;
import main.astraeus.core.net.packet.outgoing.impl.SendRegionUpdate;
import main.astraeus.core.net.packet.outgoing.impl.SendString;

/**
 * The incoming {@link IncomingPacket} responsible for handling user commands send from the client.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode(103)
public class CommandPacketListener implements IncomingPacketListener {

      /**
       * The single logger for this class.
       */
      public static final Logger logger = Logger.getLogger(CommandPacketListener.class.getName());

      @Override
      public void handleMessage(Player player, IncomingPacket packet) {

            final PacketReader reader = packet.getReader();

            final int messageLength = packet.getLength() - 1;

            byte message[] = reader.readBytes(messageLength);

            String command[] = new String(message).split(" ");

            switch (command[0]) {

                  case "interface":
                        try {
                              player.send(new SendDisplayInterface(Integer.parseInt(command[1])));
                        } catch (Exception ex) {
                              ex.printStackTrace();
                        }
                        break;

                  case "sendstring":
                        int index = Integer.parseInt(command[1]);
                        for (int i = 0; i < index; i++) {
                              player.send(new SendString(i + "", i));
                        }
                        break;

                  case "item":
                        try {
                              int itemId = Integer.parseInt(command[1]);
                              int itemAmount = Integer.parseInt(command[2]);

                              if (itemId < 0 || itemAmount <= 0) {
                                    player.sendMessage(
                                                "Invalid input: " + command[1] + " " + command[2]);
                                    return;
                              }


                              if (player.getInventoryContainer().getFreeSlots() < itemAmount
                                          && !ItemDefinition.getDefinitions()[itemId]
                                                      .isStackable()) {
                                    player.sendMessage(
                                                "You don't have enough inventory space to spawn this item..");
                                    return;
                              }

                              player.getInventoryContainer().addItem(new Item(itemId, itemAmount));
                              player.sendMessage("You have successfully spawned " + itemAmount
                                          + "X " + ItemDefinition.getDefinitions()[itemId].getName()
                                          + ".");

                        } catch (Exception exception) {
                              player.sendMessage("Invalid syntax: " + command[0] + " " + command[1]
                                          + " " + command[2] + ".");
                        }
                        break;

                  case "debug":
                        player.setServerDebug(!player.isServerDebug() ? true : false);
                        player.sendMessage(player.isServerDebug() ? "[DEBUG MODE] - ON"
                                    : "[DEBUG MODE] - OFF");
                        break;

                  case "region":
                        player.send(new SendRegionUpdate());
                        break;

                  default:
                        logger.log(Level.INFO, String.format("Unknown command: %s", command[0]));
                        break;
            }
      }

}
