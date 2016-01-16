package main.astraeus.core.net.packet.outgoing.impl;

import java.util.Iterator;

import main.astraeus.core.game.World;
import main.astraeus.core.game.model.Position;
import main.astraeus.core.game.model.entity.mobile.npc.Npc;
import main.astraeus.core.game.model.entity.mobile.npc.update.NpcUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.npc.update.impl.NpcAnimationUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.npc.update.impl.NpcDoubleHitUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.npc.update.impl.NpcFaceCoordinateUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.npc.update.impl.NpcForceChatUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.npc.update.impl.NpcGraphicsUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.npc.update.impl.NpcInteractionUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.npc.update.impl.NpcSingleHitUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.packet.PacketHeader;
import main.astraeus.core.net.packet.outgoing.OutgoingPacket;
import main.astraeus.core.net.protocol.codec.ByteAccess;

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
	public PacketBuilder encode(Player player) {
		
		PacketBuilder update = new PacketBuilder();
		
		builder.setAccessType(ByteAccess.BIT_ACCESS);
		
		builder.putBits(8, player.getLocalNpcs().size());
		
		for (final Iterator<Npc> iterator = player.getLocalNpcs().iterator(); iterator.hasNext();) {
			
			final Npc npc = iterator.next();
			
			if (World.getNpcs()[npc.getIndex()] != null && npc.isRegistered() && player.getPosition().isWithinDistance(npc.getPosition(),  Position.VIEWING_DISTANCE)) {
				updateMovement(npc, builder);
				
				if (npc.getUpdateFlags().isUpdateRequired()) {
					appendUpdates(npc, update);
				}
			} else {
				iterator.remove();
				builder.putBit(true);
				builder.putBits(2, 3);
			}
	
		}
		
		for (final Npc npc : World.getNpcs()) {
			
			if (player.getLocalNpcs().size() >= 255) {
				break;
			}
			
			if (npc == null || player.getLocalNpcs().contains(npc) || !npc.isRegistered()) {
				continue;
			}
			
			if (npc.getPosition().isWithinDistance(player.getPosition(), Position.VIEWING_DISTANCE)) {
				addNPC(npc, player, builder);
				
				if (npc.getUpdateFlags().isUpdateRequired()) {
					appendUpdates(npc, update);
				}				
			}			
		}
		
		if (update.getBuffer().position() > 0) {
			builder.putBits(14, 16383)
			.setAccessType(ByteAccess.BYTE_ACCESS)
			.putBytes(update.getBuffer());
		} else {
			builder.setAccessType(ByteAccess.BYTE_ACCESS);
		}

		return builder;
	}

	/**
	 * Updates an npc's movement queue.
	 * 
	 * @param npc
	 *            The npc that will be updated.
	 * 
	 * @param builder
	 *            The builder used to place data into a buffer.
	 */
	public static void updateMovement(Npc npc, PacketBuilder builder) {
		if (npc.getWalkingDirection() == -1) {
			if (npc.getUpdateFlags().isUpdateRequired()) {
				builder.putBit(true)
				.putBits(2, 0);
			} else {
				builder.putBit(false);
			}
		} else {
			builder.putBit(true)
			.putBits(2, 1)
			.putBits(3, npc.getWalkingDirection())
			.putBit(npc.getUpdateFlags().isUpdateRequired());
		}
	}

	/**
	 * Displays an NPC on a players client.
	 * 
	 * @param npc
	 *            The npc to display.
	 * 
	 * @param player
	 *            The player to display the npc for.
	 * 
	 * @param builder
	 *            The builder used to place data into a buffer.
	 * 
	 */
	public static void addNPC(Npc npc, Player player, PacketBuilder builder) {
		player.getLocalNpcs().add(npc);
		builder.putBits(12, npc.getIndex())
		.putBits(5, npc.getPosition().getY() - player.getPosition().getY())
		.putBits(5, npc.getPosition().getX() - player.getPosition().getY())
		.putBit(npc.getUpdateFlags().isUpdateRequired())
		.putBits(12, npc.getId())
		.putBit(true);
	}
	
	/**
	 * Appends a single {@link Mob}s update block to the main update block.
	 * 
	 * @param block
	 *            The block to append.
	 * 
	 * @param npc
	 *            The mob to update.
	 * 
	 * @param buffer
	 *            The buffer to store data.
	 */
	public void append(NpcUpdateBlock block, Npc npc, PacketBuilder builder) {
		block.encode(npc, builder);		
	}

	/**
	 * Appends a mask update for an npc.
	 * 
	 * @param npc
	 *            The npc that the update masks are for.
	 * 
	 * @param update
	 *            The update buffer to place data in.
	 */
	public void appendUpdates(Npc npc, PacketBuilder update) {
		
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

		if (npc.getUpdateFlags().get(UpdateFlag.FORCED_CHAT) && npc.getForcedChat().length() > 0) {
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

		builder.putByte(updateMask);

		if (npc.getUpdateFlags().get(UpdateFlag.ANIMATION)) {
			append(new NpcAnimationUpdateBlock(), npc, builder);
		}

		if (npc.getUpdateFlags().get(UpdateFlag.DOUBLE_HIT)) {
			append(new NpcDoubleHitUpdateBlock(), npc, builder);
		}

		if (npc.getUpdateFlags().get(UpdateFlag.GRAPHICS)) {
			append(new NpcGraphicsUpdateBlock(), npc, builder);
		}

		if (npc.getUpdateFlags().get(UpdateFlag.ENTITY_INTERACTION)) {
			append(new NpcInteractionUpdateBlock(), npc, builder);
		}

		if (npc.getUpdateFlags().get(UpdateFlag.FORCED_CHAT) && npc.getForcedChat().length() > 0) {
			append(new NpcForceChatUpdateBlock(), npc, builder);
		}

		if (npc.getUpdateFlags().get(UpdateFlag.SINGLE_HIT)) {
			append(new NpcSingleHitUpdateBlock(), npc, builder);
		}

		if (npc.getUpdateFlags().get(UpdateFlag.FACE_COORDINATE)) {
			append(new NpcFaceCoordinateUpdateBlock(), npc, builder);
		}
	}

}
