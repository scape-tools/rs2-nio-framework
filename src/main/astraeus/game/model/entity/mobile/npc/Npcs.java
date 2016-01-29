package main.astraeus.game.model.entity.mobile.npc;

import main.astraeus.game.model.Position;
import main.astraeus.game.model.World;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.utility.IntUtils;

public class Npcs {
	
	public static void createNpc(NpcSpawn spawn) {
		
		int slot = IntUtils.findFreeIndex(World.getNpcs());
		
		if (slot == -1) {
			return;
		}
		
		final Npc npc = new Npc(spawn.getId(), slot);
		npc.setPosition(new Position(3094, 3491));
		npc.setInitialPosition(new Position(3094, 3491));		
		npc.setRegistered(true);
		
		npc.getUpdateFlags().flag(UpdateFlag.APPEARANCE);
		
		World.getNpcs()[slot] = npc;
		
		System.out.println("spawned an npc.");
	}

}
