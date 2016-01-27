package main.astraeus.game.model.entity.mobile.npc.update.impl;

import main.astraeus.game.model.entity.mobile.npc.Npc;
import main.astraeus.game.model.entity.mobile.npc.update.NpcUpdateBlock;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.net.packet.PacketWriter;

public class NpcSingleHitUpdateBlock extends NpcUpdateBlock {

	public NpcSingleHitUpdateBlock() {
		super(0x20, UpdateFlag.SINGLE_HIT);
	}

	@Override
	public void encode(Npc entity, PacketWriter builder) {
		
	}

}
