package main.astraeus.net.packet.incoming.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.PacketReader;
import main.astraeus.net.packet.incoming.IncomingPacket;
import main.astraeus.net.packet.incoming.IncomingPacketListener;
import main.astraeus.net.packet.incoming.IncomingPacketOpcode;
import main.astraeus.net.packet.outgoing.impl.SendDisplayInterface;
import main.astraeus.net.packet.outgoing.impl.SendRegionUpdate;
import main.astraeus.net.packet.outgoing.impl.SendString;

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
      public void handlePacket(Player player, IncomingPacket packet) {

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

                  case "debug":
                        player.setDebugMode(player.isDebugMode() ? false : true);
                        player.sendMessage(player.isDebugMode() ? "[DEBUG MODE= ON]"
                                    : "[DEBUG MODE= OFF]");
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
