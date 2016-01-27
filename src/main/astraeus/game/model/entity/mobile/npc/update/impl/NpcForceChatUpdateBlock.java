package main.astraeus.game.model.entity.mobile.npc.update.impl;

import main.astraeus.game.model.entity.mobile.npc.Npc;
import main.astraeus.game.model.entity.mobile.npc.update.NpcUpdateBlock;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.net.packet.PacketWriter;

public class NpcForceChatUpdateBlock extends NpcUpdateBlock {

	public NpcForceChatUpdateBlock() {
		super(4, UpdateFlag.FORCED_CHAT);
	}

	@Override
	public void encode(Npc entity, PacketWriter builder) {
		
	}

}
