package main.astraeus.game.model.entity.mobile.npc.update.impl;

import main.astraeus.game.model.entity.mobile.npc.Npc;
import main.astraeus.game.model.entity.mobile.npc.update.NpcUpdateBlock;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.net.packet.PacketWriter;

public class NpcGraphicsUpdateBlock extends NpcUpdateBlock {

	public NpcGraphicsUpdateBlock() {
		super(0x100, UpdateFlag.GRAPHICS);
	}

	@Override
	public void encode(Npc entity, PacketWriter builder) {
		
	}

}
