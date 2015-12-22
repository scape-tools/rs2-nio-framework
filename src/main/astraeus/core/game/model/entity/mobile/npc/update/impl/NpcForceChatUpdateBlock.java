package main.astraeus.core.game.model.entity.mobile.npc.update.impl;

import main.astraeus.core.game.model.entity.mobile.npc.Npc;
import main.astraeus.core.game.model.entity.mobile.npc.update.NpcUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.channel.packet.PacketBuilder;

public class NpcForceChatUpdateBlock extends NpcUpdateBlock {

	public NpcForceChatUpdateBlock() {
		super(UpdateFlag.FORCED_CHAT);
	}

	@Override
	public void write(Npc entity, PacketBuilder builder) {
		
	}

}
