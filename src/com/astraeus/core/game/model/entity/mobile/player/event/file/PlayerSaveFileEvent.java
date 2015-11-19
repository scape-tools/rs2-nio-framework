package com.astraeus.core.game.model.entity.mobile.player.event.file;

import java.io.FileWriter;
import java.io.IOException;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.model.entity.mobile.player.event.PlayerFileEvent;
import com.astraeus.core.game.utility.Writable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class PlayerSaveFileEvent extends PlayerFileEvent implements Writable {

	public PlayerSaveFileEvent(Player player) {
		super(player);
	}

	@Override
	public boolean serialize() {
		try {
			
			if (!getFile().exists()) {
				getFile().createNewFile();
			}
			
			final Gson builder = new GsonBuilder().setPrettyPrinting().create();
			final JsonObject writer = new JsonObject();

			writer.addProperty("username", getPlayer().getDetails().getUsername());
			writer.addProperty("password", getPlayer().getDetails().getPassword());
			writer.add("position", builder.toJsonTree(getPlayer().getPosition()));

			FileWriter fileWriter = new FileWriter(getFile());
			fileWriter.write(builder.toJson(writer));
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
