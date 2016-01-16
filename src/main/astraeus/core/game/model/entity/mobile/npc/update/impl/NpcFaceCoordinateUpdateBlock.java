package main.astraeus.core.game.model.entity.mobile.npc.update.impl;

import main.astraeus.core.game.model.entity.mobile.npc.Npc;
import main.astraeus.core.game.model.entity.mobile.npc.update.NpcUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.packet.PacketBuilder;

public class NpcFaceCoordinateUpdateBlock extends NpcUpdateBlock {

	public NpcFaceCoordinateUpdateBlock() {
		super(2, UpdateFlag.FACE_COORDINATE);
	}

	@Override
	public void encode(Npc entity, PacketBuilder builder) {
		
	}

}
