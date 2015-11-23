package com.astraeus.core.game.model.entity.mobile.npc.update.impl;

import com.astraeus.core.game.model.entity.Position;
import com.astraeus.core.game.model.entity.UpdateFlags;
import com.astraeus.core.game.model.entity.mobile.npc.Npc;
import com.astraeus.core.game.model.entity.mobile.npc.update.UpdateBlock;
import com.astraeus.core.game.model.entity.mobile.player.Player.Attributes;
import com.astraeus.core.net.channel.packet.PacketBuilder;
import com.astraeus.core.net.channel.protocol.codec.game.ByteOrder;

public class StatefulUpdateBlock extends UpdateBlock {

	@Override
	public void update(Npc npc, PacketBuilder builder) {
		
		int mask = 0x4;
		
		if (npc.getUpdateFlags().contains(UpdateFlags.FACE_POSITION)) {
			mask |= 0x4;
		}
		
		builder.putByte(mask);
		
		if (npc.getUpdateFlags().contains(UpdateFlags.FACE_POSITION)) {
			final Position position = (Position) npc.getAttributes().get(Attributes.FACE_DIRECTION);
			builder.putShort(position.getX() * 2 + 1, ByteOrder.LITTLE);
			builder.putShort(position.getY() * 2 + 1, ByteOrder.LITTLE);
		}		
	}

}
