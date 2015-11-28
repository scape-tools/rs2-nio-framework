package com.astraeus.core.game.model.entity.mobile.npc;

import com.astraeus.core.Server;
import com.astraeus.core.game.model.entity.mobile.EntityIndex;
import com.astraeus.core.utility.Utilities;

public class Npcs {
	
	public static void createNpc(NpcSpawn spawn) {
		
		//npc serialized successfully
		System.out.println("Id: " + spawn.getId());
		System.out.println("Pos: " + spawn.getPosition().toString());
		System.out.println("Facing: " + spawn.getFacing().name());
		
		int slot = Utilities.findFreeIndex(EntityIndex.getMobIndexes());
		
		System.out.println("Slot: " + slot);
		
		Npc npc = new Npc(spawn.getId(), slot);
		npc.setIndex(slot);
		Server.getUpdateProcessor().getNpcs().put(npc.getIndex(), npc); //for processing
		npc.setInitialPosition(spawn.getPosition());
		npc.setPosition(spawn.getPosition());
	}

}
