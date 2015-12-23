package main.astraeus.core.game.model.entity.mobile.npc.update.impl;

import main.astraeus.core.game.model.entity.mobile.npc.Npc;
import main.astraeus.core.game.model.entity.mobile.npc.update.NpcUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.channel.packet.PacketBuilder;

public class NpcAnimationUpdateBlock extends NpcUpdateBlock {

	public NpcAnimationUpdateBlock() {
		super(8, UpdateFlag.ANIMATION);
	}

	@Override
	public void encode(Npc entity, PacketBuilder builder) {

	}

}

