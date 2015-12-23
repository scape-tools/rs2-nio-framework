package main.astraeus.core.game.model.entity.mobile.npc;

import main.astraeus.core.game.model.entity.Position;

public class Npcs {
	
	public static void createNpc() {
		
		Npc npc = new Npc(0, 0);

		
		npc.setRegistered(true);
		npc.setPosition(new Position(3094, 3491));
		npc.setInitialPosition(new Position(3094, 3491));
		
		//World.getNpcList().add(npc);
		
		System.out.println("spawned an npc.");
	}

}
