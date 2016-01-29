package main.astraeus.net.packet.outgoing.impl;

import java.util.Iterator;

import main.astraeus.game.model.Position;
import main.astraeus.game.model.World;
import main.astraeus.game.model.entity.mobile.npc.Npc;
import main.astraeus.game.model.entity.mobile.npc.update.NpcUpdateBlock;
import main.astraeus.game.model.entity.mobile.npc.update.impl.NpcAnimationUpdateBlock;
import main.astraeus.game.model.entity.mobile.npc.update.impl.NpcDoubleHitUpdateBlock;
import main.astraeus.game.model.entity.mobile.npc.update.impl.NpcFaceCoordinateUpdateBlock;
import main.astraeus.game.model.entity.mobile.npc.update.impl.NpcForceChatUpdateBlock;
import main.astraeus.game.model.entity.mobile.npc.update.impl.NpcGraphicsUpdateBlock;
import main.astraeus.game.model.entity.mobile.npc.update.impl.NpcInteractionUpdateBlock;
import main.astraeus.game.model.entity.mobile.npc.update.impl.NpcSingleHitUpdateBlock;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.net.packet.PacketHeader;
import main.astraeus.net.packet.PacketWriter;
import main.astraeus.net.packet.outgoing.OutgoingPacket;
import main.astraeus.net.protocol.codec.AccessType;

/**
 * The {@link OutgoingPacket} that updates a non-player character.
 * 
 * @author SeVen
 */
public class SendNPCUpdate extends OutgoingPacket {

      /**
       * Creates a new {@link SendNPCUpdate}.
       */
      public SendNPCUpdate() {
            super(65, PacketHeader.VARIABLE_SHORT, 16384);
      }

      @Override
      public PacketWriter encode(Player player) {

            PacketWriter update = new PacketWriter();

            player.getContext().prepare(this, writer);
            
            writer.setAccessType(AccessType.BIT_ACCESS);

            writer.writeBits(8, player.getLocalNpcs().size());

            for (final Iterator<Npc> iterator = player.getLocalNpcs().iterator(); iterator
                        .hasNext();) {

                  final Npc npc = iterator.next();

                  if (World.getNpcs()[npc.getSlot()] != null && npc.isRegistered()
                              && player.getPosition().isWithinDistance(npc.getPosition(),
                                          Position.VIEWING_DISTANCE)) {
                        updateMovement(npc, writer);

                        if (npc.getUpdateFlags().isUpdateRequired()) {
                              appendUpdates(npc, update);
                        }
                  } else {
                        iterator.remove();
                        writer.writeBit(true);
                        writer.writeBits(2, 3);
                  }

            }

            for (final Npc npc : World.getNpcs()) {

                  if (player.getLocalNpcs().size() >= 255) {
                        break;
                  }

                  if (npc == null || player.getLocalNpcs().contains(npc) || !npc.isRegistered()) {
                        continue;
                  }

                  if (npc.getPosition().isWithinDistance(player.getPosition(),
                              Position.VIEWING_DISTANCE)) {
                        addNPC(npc, player, writer);

                        if (npc.getUpdateFlags().isUpdateRequired()) {
                              appendUpdates(npc, update);
                        }
                  }
            }

            if (update.getBuffer().position() > 0) {
                  writer.writeBits(14, 16383).setAccessType(AccessType.BYTE_ACCESS)
                              .writeBytes(update.getBuffer());
            } else {
                  writer.setAccessType(AccessType.BYTE_ACCESS);
            }

            return writer;
      }

      /**
       * Updates an npc's movement queue.
       * 
       * @param npc The npc that will be updated.
       * 
       * @param writer The writer used to place data into a buffer.
       */
      public static void updateMovement(Npc npc, PacketWriter writer) {
            if (npc.getWalkingDirection() == -1) {
                  if (npc.getUpdateFlags().isUpdateRequired()) {
                        writer.writeBit(true).writeBits(2, 0);
                  } else {
                        writer.writeBit(false);
                  }
            } else {
                  writer.writeBit(true).writeBits(2, 1).writeBits(3, npc.getWalkingDirection())
                              .writeBit(npc.getUpdateFlags().isUpdateRequired());
            }
      }

      /**
       * Displays an NPC on a players client.
       * 
       * @param npc The npc to display.
       * 
       * @param player The player to display the npc for.
       * 
       * @param writer The writer used to place data into a buffer.
       * 
       */
      public static void addNPC(Npc npc, Player player, PacketWriter writer) {
            player.getLocalNpcs().add(npc);
            writer.writeBits(12, npc.getSlot())
                        .writeBits(5, npc.getPosition().getY() - player.getPosition().getY())
                        .writeBits(5, npc.getPosition().getX() - player.getPosition().getY())
                        .writeBit(npc.getUpdateFlags().isUpdateRequired())
                        .writeBits(12, npc.getId()).writeBit(true);
      }

      /**
       * Appends a single {@link Mob}s update block to the main update block.
       * 
       * @param block The block to append.
       * 
       * @param npc The mob to update.
       * 
       * @param buffer The buffer to store data.
       */
      public void append(NpcUpdateBlock block, Npc npc, PacketWriter writer) {
            block.encode(npc, writer);
      }

      /**
       * Appends a mask update for an npc.
       * 
       * @param npc The npc that the update masks are for.
       * 
       * @param update The update buffer to place data in.
       */
      public void appendUpdates(Npc npc, PacketWriter update) {

            int updateMask = 0x0;

            if (npc.getUpdateFlags().get(UpdateFlag.ANIMATION)) {
                  updateMask |= 0x10;
            }

            if (npc.getUpdateFlags().get(UpdateFlag.DOUBLE_HIT)) {
                  updateMask |= 0x8;
            }

            if (npc.getUpdateFlags().get(UpdateFlag.GRAPHICS)) {
                  updateMask |= 0x80;
            }

            if (npc.getUpdateFlags().get(UpdateFlag.ENTITY_INTERACTION)) {
                  updateMask |= 0x20;
            }

            if (npc.getUpdateFlags().get(UpdateFlag.FORCED_CHAT)
                        && npc.getForcedChat().length() > 0) {
                  updateMask |= 0x1;
            }

            if (npc.getUpdateFlags().get(UpdateFlag.SINGLE_HIT)) {
                  updateMask |= 0x40;
            }

            if (npc.getUpdateFlags().get(UpdateFlag.TRANSFORM)) {
                  updateMask |= 0x2;
            }

            if (npc.getUpdateFlags().get(UpdateFlag.FACE_COORDINATE)) {
                  updateMask |= 0x4;
            }

            writer.write(updateMask);

            if (npc.getUpdateFlags().get(UpdateFlag.ANIMATION)) {
                  append(new NpcAnimationUpdateBlock(), npc, writer);
            }

            if (npc.getUpdateFlags().get(UpdateFlag.DOUBLE_HIT)) {
                  append(new NpcDoubleHitUpdateBlock(), npc, writer);
            }

            if (npc.getUpdateFlags().get(UpdateFlag.GRAPHICS)) {
                  append(new NpcGraphicsUpdateBlock(), npc, writer);
            }

            if (npc.getUpdateFlags().get(UpdateFlag.ENTITY_INTERACTION)) {
                  append(new NpcInteractionUpdateBlock(), npc, writer);
            }

            if (npc.getUpdateFlags().get(UpdateFlag.FORCED_CHAT)
                        && npc.getForcedChat().length() > 0) {
                  append(new NpcForceChatUpdateBlock(), npc, writer);
            }

            if (npc.getUpdateFlags().get(UpdateFlag.SINGLE_HIT)) {
                  append(new NpcSingleHitUpdateBlock(), npc, writer);
            }

            if (npc.getUpdateFlags().get(UpdateFlag.FACE_COORDINATE)) {
                  append(new NpcFaceCoordinateUpdateBlock(), npc, writer);
            }
      }

}
