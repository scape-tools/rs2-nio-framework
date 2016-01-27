package main.astraeus.core.game.model.entity.mobile.player.event.file;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import main.astraeus.core.game.model.Position;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.PlayerRights;
import main.astraeus.core.game.model.entity.mobile.player.event.PlayerFileEvent;
import main.astraeus.utility.Decodeable;

public class PlayerReadFileEvent extends PlayerFileEvent implements Decodeable {

	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(PlayerReadFileEvent.class.getName());

	public PlayerReadFileEvent(Player player) {
		super(player);
		if (!getFile().exists()) {
			getPlayer().save();
			logger.log(Level.WARNING, "Player directory does not exist.");
		}
	}

	@Override
	public boolean deserialize() {
		try {

			final JsonParser fileParser = new JsonParser();
			final Object object = fileParser.parse(new FileReader(getFile()));
			final Gson builder = new GsonBuilder().create();
			final JsonObject reader = (JsonObject) object;

			if (reader.has("ip-address")) {
				getPlayer().getDetails().setAddress(reader.get("ip-address").getAsString());
			}

			String name = reader.get("username").getAsString();

			String password = reader.get("password").getAsString();

			if (reader.has("username"))
				getPlayer().getDetails().setUsername(name);

			if (reader.has("password"))
				getPlayer().getDetails().setPassword(password);

			if (reader.has("rights")) {
				getPlayer().getDetails().setRights(PlayerRights.valueOf(reader.get("rights").getAsString()));
			}

			if (reader.has("position"))
				getPlayer().getPosition().setPosition(builder.fromJson(reader.get("position"), Position.class));

			if (!name.equals(getPlayer().getDetails().getUsername())
					|| !password.equals(getPlayer().getDetails().getPassword())) {
				return false;
			}

		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return true;
	}

}
