package main.astraeus.core.net.packet.outgoing.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.PacketWriter;
import main.astraeus.core.net.packet.outgoing.OutgoingPacket;
import main.astraeus.core.net.protocol.codec.ByteOrder;
import main.astraeus.core.net.protocol.codec.ByteModification;

/**
 * The {@link OutgoingPacket} that displays an npcs head model on an interface.
 * 
 * @author SeVen
 */
public class SendNpcDialogueHead extends OutgoingPacket {

      /**
       * The id of the npc to display.
       */
      private final int npcId;

      /**
       * The id of the interface to display the head model on.
       */
      private final int interfaceId;

      /**
       * Creates a new {@link SendNpcDialogueHead}.
       * 
       * @param npcId The id of the npc to display.
       * 
       * @param interfaceId The id of the interface to show the head model on.
       */
      public SendNpcDialogueHead(int npcId, int interfaceId) {
            super(75, 15);
            this.npcId = npcId;
            this.interfaceId = interfaceId;
      }

      @Override
      public PacketWriter encode(Player player) {
            player.getContext().prepare(this, writer);
            writer.writeShort(npcId, ByteModification.ADDITION, ByteOrder.LITTLE);
            writer.writeShort(interfaceId, ByteModification.ADDITION, ByteOrder.LITTLE);
            return writer;
      }

}
