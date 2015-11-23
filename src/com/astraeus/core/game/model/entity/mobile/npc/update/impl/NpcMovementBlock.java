package com.astraeus.core.game.model.entity.mobile.npc.update.impl;

import com.astraeus.core.game.model.entity.mobile.npc.Npc;
import com.astraeus.core.game.model.entity.mobile.npc.update.UpdateBlock;
import com.astraeus.core.net.channel.packet.PacketBuilder;

public class NpcMovementBlock extends UpdateBlock {

	@Override
	public void update(Npc npc, PacketBuilder builder) {
		if (npc.getWalkingDirection() == -1) {			
			if(npc.getUpdateFlags().isUpdateRequired()) {
				builder.putBits(1, 1);
				builder.putBits(2, 0);
				builder.putBits(1, 0);
			} else {
				builder.putBits(1, 0);
			}			
		} else {
			builder.putBits(1, 1);
			builder.putBits(2, 1);
			builder.putBits(3, npc.getWalkingDirection());
			builder.putBits(1, 1);
		}
	}

}
