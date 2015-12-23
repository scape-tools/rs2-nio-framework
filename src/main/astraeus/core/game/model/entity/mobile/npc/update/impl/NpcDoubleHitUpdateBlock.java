package main.astraeus.core.game.model.entity.mobile.npc.update.impl;

import main.astraeus.core.game.model.entity.mobile.npc.Npc;
import main.astraeus.core.game.model.entity.mobile.npc.update.NpcUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.channel.packet.PacketBuilder;

public class NpcDoubleHitUpdateBlock extends NpcUpdateBlock {

	public NpcDoubleHitUpdateBlock() {
		super(0x200, UpdateFlag.DOUBLE_HIT);
	}

	@Override
	public void encode(Npc entity, PacketBuilder buffer) {
		
	}

}
