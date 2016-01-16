package main.astraeus.core.net.packet.outgoing.impl;

import java.util.Iterator;

import main.astraeus.core.game.World;
import main.astraeus.core.game.model.Position;
import main.astraeus.core.game.model.entity.mobile.npc.Npc;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.Player.Attributes;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.packet.PacketHeader;
import main.astraeus.core.net.packet.outgoing.OutgoingPacket;
import main.astraeus.core.net.protocol.codec.ByteAccess;
import main.astraeus.core.net.protocol.codec.ByteOrder;

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
	 * Appends a mask update for an npc.
	 * 
	 * @param npc
	 *            The npc that the update masks are for.
	 * 
	 * @param update
	 *            The update buffer to place data in.
	 */
	public static void appendUpdates(Npc npc, PacketBuilder update) {

		int mask = 0x0;

		if (npc.getUpdateFlags().get(UpdateFlag.FACE_COORDINATE)) {
			mask |= 0x4;
		}

		update.putByte(mask);

		if (npc.getUpdateFlags().get(UpdateFlag.FACE_COORDINATE)) {
			final Position position = (Position) npc.getAttributes().get(Attributes.FACE_COORDINATE);
			update.putShort(position.getX() * 2 + 1, ByteOrder.LITTLE);
			update.putShort(position.getY() * 2 + 1, ByteOrder.LITTLE);
		}
	}

}
