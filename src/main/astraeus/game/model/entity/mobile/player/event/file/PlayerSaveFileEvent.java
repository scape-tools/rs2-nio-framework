package main.astraeus.game.model.entity.mobile.player.event.file;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.player.PlayerConstants;
import main.astraeus.game.model.entity.mobile.player.event.PlayerFileEvent;
import main.astraeus.utility.Encodeable;

public class PlayerSaveFileEvent extends PlayerFileEvent implements Encodeable {

	public PlayerSaveFileEvent(Player player) {
		super(player);
	}
	
	private boolean newPlayerFlag = false;

	@Override
	public boolean serialize() {
		try {
			
			if (!getFile().exists()) {
				getFile().createNewFile();
				newPlayerFlag = true;
			}
			
			final Gson builder = new GsonBuilder().setPrettyPrinting().create();
			final JsonObject writer = new JsonObject();

			writer.addProperty("ip-address", getPlayer().getDetails().getAddress());
			writer.addProperty("username", getPlayer().getDetails().getUsername());
			writer.addProperty("password", getPlayer().getDetails().getPassword());
			writer.addProperty("rights", getPlayer().getDetails().getRights().name());
			writer.add("position", builder.toJsonTree(newPlayerFlag ? PlayerConstants.START_COORDINATES : getPlayer().getPosition()));

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
