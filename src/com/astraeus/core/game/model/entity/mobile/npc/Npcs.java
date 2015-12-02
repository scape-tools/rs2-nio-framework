package com.astraeus.core.game.model.entity.mobile.npc;

import com.astraeus.core.game.World;

public class Npcs {
	
	public static void createNpc(NpcSpawn spawn) {
		
		int slot = World.getNpcList().calculateIndex();
		
		System.out.println("Slot: " + slot);
		
		Npc npc = new Npc(spawn.getId(), slot);
		npc.setIndex(slot);
		npc.setInitialPosition(spawn.getPosition());
		npc.setPosition(spawn.getPosition());
		World.getNpcList().add(npc);
	}

}
