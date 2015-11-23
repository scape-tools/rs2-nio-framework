package com.astraeus.core.net.channel.packet.outgoing;

import java.nio.ByteBuffer;
import java.util.Iterator;

import com.astraeus.core.Server;
import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.model.entity.mobile.player.update.UpdateBlock;
import com.astraeus.core.game.model.entity.mobile.player.update.impl.PlayerMovementBlock;
import com.astraeus.core.game.model.entity.mobile.player.update.impl.RegionalMovementBlock;
import com.astraeus.core.game.model.entity.mobile.player.update.impl.StatefulUpdateBlock;
import com.astraeus.core.net.channel.packet.OutgoingPacket;
import com.astraeus.core.net.channel.packet.PacketBuilder;
import com.astraeus.core.net.channel.packet.PacketHeader;
import com.astraeus.core.net.channel.protocol.codec.game.ByteAccess;

public final class PlayerUpdatePacket extends OutgoingPacket {

	public PlayerUpdatePacket() {
		super(81, PacketHeader.VARIABLE_SHORT, 16384);
	}

	public final void append(UpdateBlock block, PacketBuilder builder, Player player) {
		block.update(player, builder);
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		
		PacketBuilder update = new PacketBuilder(ByteBuffer.allocate(8192));
		
		player.getContext().prepare(this, builder);

		builder.setAccessType(ByteAccess.BIT_ACCESS);

		append(new PlayerMovementBlock(), builder, player);

		if (player.updateRequired()) {

			append(new StatefulUpdateBlock(false), update, player);
		}

		builder.putBits(8, player.getLocalPlayers().size());

		for (Iterator<Player> iterator = player.getLocalPlayers().iterator(); iterator.hasNext();) {

			final Player other = iterator.next();

			if (other.getPosition().isWithinDistance(player.getPosition(), 15)) {

				append(new RegionalMovementBlock(), builder, other);

				if (other.updateRequired()) {

					append(new StatefulUpdateBlock(false), update, other);

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

				append(new StatefulUpdateBlock(true), update, other);

			}
		}

		if (update.getInternal().position() > 0) {

			builder.putBits(11, 2047);

			builder.setAccessType(ByteAccess.BYTE_ACCESS);

			builder.putBytes(update.getInternal());

		} else {

			builder.setAccessType(ByteAccess.BYTE_ACCESS);
		}

		return builder;
	}
}