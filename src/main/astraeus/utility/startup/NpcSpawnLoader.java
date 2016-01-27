package main.astraeus.utility.startup;

import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import main.astraeus.Configuration;
import main.astraeus.game.model.Direction;
import main.astraeus.game.model.Position;
import main.astraeus.game.model.entity.mobile.npc.NpcSpawn;
import main.astraeus.game.model.entity.mobile.npc.Npcs;
import main.astraeus.utility.JsonLoader;

public class NpcSpawnLoader extends JsonLoader {

	public NpcSpawnLoader() {
		super(Configuration.DATA + "json/npc_spawns.json");
		load();
		//System.out.println("Loaded: " + World.getNpcList().getSize() + " npc spawn.");
	}

	@Override
	public void load(JsonObject reader, Gson builder) {
		int id = reader.get("id").getAsInt();
		Position position = builder.fromJson(reader.get("position"), Position.class);
		boolean randomWalk = reader.get("randomWalk").getAsBoolean();
		String facing = Objects.requireNonNull(reader.get("facing").getAsString());
		NpcSpawn spawn = new NpcSpawn(id, position, randomWalk, Direction.valueOf(facing));
		Npcs.createNpc(spawn);
	}

}
