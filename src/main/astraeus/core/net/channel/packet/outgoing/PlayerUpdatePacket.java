package main.astraeus.core.net.channel.packet.outgoing;

import java.nio.ByteBuffer;
import java.util.Iterator;

import main.astraeus.core.Server;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.player.update.impl.PlayerAppearanceUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.channel.packet.OutgoingPacket;
import main.astraeus.core.net.channel.packet.PacketBuilder;
import main.astraeus.core.net.channel.packet.PacketHeader;
import main.astraeus.core.net.channel.protocol.codec.game.ByteAccess;

public final class PlayerUpdatePacket extends OutgoingPacket {

	public PlayerUpdatePacket() {
		super(81, PacketHeader.VARIABLE_SHORT, 16384);
	}

	@Override
	public PacketBuilder dispatch(Player player) {		
		if(player.getUpdateFlags().get(UpdateFlag.REGION_CHANGING)) {
			player.sendRegionalUpdate();
		}
		
		PacketBuilder update = new PacketBuilder(ByteBuffer.allocate(8192));
		
		player.getContext().prepare(this, builder);

		builder.setAccessType(ByteAccess.BIT_ACCESS);

		updateMovement(player, builder);

		if (player.getUpdateFlags().isUpdateRequired()) {

			appendUpdates(player, update, false);
		}

		builder.putBits(8, player.getLocalPlayers().size());

		for (Iterator<Player> iterator = player.getLocalPlayers().iterator(); iterator.hasNext();) {

			final Player other = iterator.next();

			if (other.getPosition().isWithinDistance(player.getPosition(), 15)) {

				updateMovement(other, builder);

				if (other.getUpdateFlags().isUpdateRequired()) {

					appendUpdates(other, update, false);

				}

			} else {

				iterator.remove();

				builder.putBits(1, 1);

				builder.putBits(2, 3);
			}
		}

		for (Player other : Server.getUpdateProcessor().getPlayers().values()) {

			if (other.getLocalPlayers().size() >= 255) {

				break;
			}

			if (other == player || player.getLocalPlayers().contains(other)) {

				continue;

			}
			if (other.getPosition().isWithinDistance(player.getPosition(), 15)) {

				player.getLocalPlayers().add(other);

				builder.putBits(11, other.getIndex());

				builder.putBits(1, 1);

				builder.putBits(1, 1);

				builder.putBits(5, other.getPosition().getY() - player.getPosition().getY());

				builder.putBits(5, other.getPosition().getX() - player.getPosition().getX());

				appendUpdates(other, update, true);

			}
		}

		if (update.getBuffer().position() > 0) {

			builder.putBits(11, 2047);

			builder.setAccessType(ByteAccess.BYTE_ACCESS);

			builder.putBytes(update.getBuffer());

		} else {

			builder.setAccessType(ByteAccess.BYTE_ACCESS);
		}

		return builder;
	}
	
	public void updateMovement(Player player, PacketBuilder buffer) {
		if (player.getUpdateFlags().get(UpdateFlag.REGION_CHANGING)) {
			buffer.putBits(1, 1);
			buffer.putBits(2, 3);
			buffer.putBits(2, player.getPosition().getHeight());
			buffer.putBits(1, 1);
			buffer.putBits(1, player.getUpdateFlags().isUpdateRequired() ? 1 : 0);
			buffer.putBits(7, player.getPosition().getLocalY(player.getLastPosition()));
			buffer.putBits(7, player.getPosition().getLocalX(player.getLastPosition()));
		} else {
			if (player.getWalkingDirection() == -1) {
				if (player.getUpdateFlags().isUpdateRequired()) {
					buffer.putBits(1, 1);
					buffer.putBits(2, 0);
				} else {
					buffer.putBits(1, 0);
				}
			} else {
				if (player.getRunningDirection() == -1) {
					buffer.putBits(1, 1);
					buffer.putBits(2, 1);
					buffer.putBits(3, player.getWalkingDirection());
					buffer.putBits(1, player.getUpdateFlags().isUpdateRequired() ? 1 : 0);
				} else {
					buffer.putBits(1, 1);
					buffer.putBits(2, 2);
					buffer.putBits(3, player.getWalkingDirection());
					buffer.putBits(3, player.getRunningDirection());
					buffer.putBits(1, player.getUpdateFlags().isUpdateRequired() ? 1 : 0);
				}
			}
		}
	}
	
	public void append(PlayerUpdateBlock block, Player player, PacketBuilder buffer) {
		block.write(player, buffer);
	}
	
	public void appendUpdates(Player player, PacketBuilder buffer, boolean forceful) {		
		synchronized(player) {
			/*
			 * The mask to denote specific updates.
			 */
			int mask = 0x0;
			if (player.getUpdateFlags().get(UpdateFlag.APPEARANCE) || forceful) {
				mask |= 0x10;
			}
			if (mask >= 0x100) {
				mask |= 0x40;
				buffer.putByte((mask & 0xFF));
				buffer.putByte((mask >> 8));
			} else {
				buffer.putByte((mask));
			}
			if (player.getUpdateFlags().get(UpdateFlag.APPEARANCE) || forceful) {				
				append(new PlayerAppearanceUpdateBlock(), player, buffer);
			}
		}
	}
	
}