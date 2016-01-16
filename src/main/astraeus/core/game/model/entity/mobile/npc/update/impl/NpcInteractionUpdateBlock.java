package main.astraeus.core.game.model.entity.mobile.npc.update.impl;

import main.astraeus.core.game.model.entity.mobile.npc.Npc;
import main.astraeus.core.game.model.entity.mobile.npc.update.NpcUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.packet.PacketBuilder;

public class NpcInteractionUpdateBlock extends NpcUpdateBlock {

	public NpcInteractionUpdateBlock() {
		super(1, UpdateFlag.FACE_ENTITY);
	}

	@Override
	public void encode(Npc entity, PacketBuilder builder) {
		
	}

}
