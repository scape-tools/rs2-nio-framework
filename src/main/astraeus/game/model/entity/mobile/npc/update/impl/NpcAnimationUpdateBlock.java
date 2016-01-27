package main.astraeus.game.model.entity.mobile.npc.update.impl;

import main.astraeus.game.model.entity.mobile.npc.Npc;
import main.astraeus.game.model.entity.mobile.npc.update.NpcUpdateBlock;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.net.packet.PacketWriter;

public class NpcAnimationUpdateBlock extends NpcUpdateBlock {

	public NpcAnimationUpdateBlock() {
		super(8, UpdateFlag.ANIMATION);
	}

	@Override
	public void encode(Npc entity, PacketWriter builder) {

	}

}

