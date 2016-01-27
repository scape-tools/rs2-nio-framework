package main.astraeus.net.packet.incoming.impl;

import main.astraeus.game.model.World;
import main.astraeus.game.model.entity.mobile.npc.Npc;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.PacketReader;
import main.astraeus.net.packet.incoming.IncomingPacket;
import main.astraeus.net.packet.incoming.IncomingPacketConstants;
import main.astraeus.net.packet.incoming.IncomingPacketListener;
import main.astraeus.net.packet.incoming.IncomingPacketOpcode;
import main.astraeus.net.protocol.codec.ByteModification;
import main.astraeus.net.protocol.codec.ByteOrder;

/**
 * The {@link IncomingPacket} responsible for the different options while clicking an npc.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode({IncomingPacketConstants.ATTACK_NPC, IncomingPacketConstants.MAGIC_ON_NPC,
            IncomingPacketConstants.NPC_ACTION_1, IncomingPacketConstants.NPC_ACTION_2,
            IncomingPacketConstants.NPC_ACTION_3})
public class NPCInteractionPacketListener implements IncomingPacketListener {

      @Override
      public void handleMessage(final Player player, IncomingPacket packet) {

            PacketReader reader = packet.getReader();
            
            switch (packet.getOpcode()) {

                  case IncomingPacketConstants.ATTACK_NPC:
                        handleAttackNpc(player, reader);
                        break;

                  case IncomingPacketConstants.MAGIC_ON_NPC:
                        handleMagicOnNpc(player, reader);
                        break;

                  case IncomingPacketConstants.NPC_ACTION_1:
                        handleFirstClickNpc(player, reader);
                        break;

                  case IncomingPacketConstants.NPC_ACTION_2:
                        handleSecondClickNpc(player, reader);
                        break;

                  case IncomingPacketConstants.NPC_ACTION_3:
                        handleThirdClickNpc(player, reader);
                        break;
            }
      }

      /**
       * Handles the action of attacking a Npc.
       * 
       * @param player The player attacking the Npc.
       * 
       * @param packet The packet for this action.
       */
      private void handleAttackNpc(Player player, PacketReader reader) {

      }

      /**
       * Handles the action of using magic on a npc
       * 
       * @param player The player using magic on this npc.
       * 
       * @param packet The packet for this action.
       */
      private void handleMagicOnNpc(Player player, PacketReader reader) {

      }

      /**
       * Handles the action of using the first interaction option on a npc.
       * 
       * @param player The player clicking on the npc.
       * 
       * @param packet The packet for this action.
       */
      private void handleFirstClickNpc(Player player, PacketReader reader) {

            int npcIndex = reader.readShort(ByteOrder.LITTLE);
            
            final Npc npc = World.getNpcs()[npcIndex];

            if (player == null || npc == null) {
                  return;
            }

      }

      /**
       * Handles the action of using the second interaction option on a npc.
       * 
       * @param player The player clicking on the npc.
       * 
       * @param packet The packet for this action.
       */
      private void handleSecondClickNpc(Player player, PacketReader reader) {
            int npcIndex = reader.readShort(ByteOrder.LITTLE, ByteModification.ADDITION);
            
            final Npc npc = World.getNpcs()[npcIndex];            

            if (player == null || npc == null) {
                  return;
            }

      }

      /**
       * Handles the action of using the third interaction option on a npc.
       * 
       * @param player The player clicking on the npc.
       * 
       * @param packet The packet for this action.
       */
      private void handleThirdClickNpc(Player player, PacketReader reader) {

            final int npcIndex = reader.readShort();            

            final Npc npc = World.getNpcs()[npcIndex];            

            if (player == null || npc == null) {
                  return;
            }
            
      }

}
