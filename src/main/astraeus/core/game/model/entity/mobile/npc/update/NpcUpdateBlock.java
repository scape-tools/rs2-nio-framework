package main.astraeus.core.game.model.entity.mobile.npc.update;

import main.astraeus.core.game.model.entity.mobile.npc.Npc;
import main.astraeus.core.game.model.entity.mobile.update.UpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;

public abstract class NpcUpdateBlock extends UpdateBlock<Npc> {

	public NpcUpdateBlock(UpdateFlag flag) {
		super(flag);
	}

}
