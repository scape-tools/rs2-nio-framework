package main.astraeus.game.model.entity.mobile.npc.update;

import main.astraeus.game.model.entity.mobile.npc.Npc;
import main.astraeus.game.model.entity.mobile.update.UpdateBlock;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;

public abstract class NpcUpdateBlock extends UpdateBlock<Npc> {

	public NpcUpdateBlock(int mask, UpdateFlag flag) {
		super(mask, flag);
	}

}
