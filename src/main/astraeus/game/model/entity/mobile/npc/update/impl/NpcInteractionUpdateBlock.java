package main.astraeus.game.model.entity.mobile.npc.update.impl;

import main.astraeus.game.model.entity.mobile.npc.Npc;
import main.astraeus.game.model.entity.mobile.npc.update.NpcUpdateBlock;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.net.packet.PacketWriter;

public class NpcInteractionUpdateBlock extends NpcUpdateBlock {

	public NpcInteractionUpdateBlock() {
		super(1, UpdateFlag.ENTITY_INTERACTION);
	}

	@Override
	public void encode(Npc entity, PacketWriter builder) {
		
	}

}
