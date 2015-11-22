package com.astraeus.core.net.channel.packet.outgoing.impl;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.OutgoingPacket;
import com.astraeus.core.net.channel.packet.PacketBuilder;
import com.astraeus.core.net.channel.packet.PacketHeader;
import com.astraeus.core.net.channel.protocol.codec.game.ByteOrder;
import com.astraeus.core.net.channel.protocol.codec.game.ByteValue;

public class RegionalUpdate extends OutgoingPacket {

	public RegionalUpdate() {
		super(73, PacketHeader.STANDARD, 5);
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		builder.putShort(player.getPosition().getRegionalX() + 6,
				ByteValue.ADDITION, ByteOrder.BIG);
		builder.putShort(player.getPosition().getRegionalY() + 6);
		player.setLastPosition(player.getPosition());
		return builder;
	}

}
