package main.astraeus.utility.startup;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import main.astraeus.Configuration;
import main.astraeus.game.model.entity.item.ItemDefinition;
import main.astraeus.game.model.entity.item.ItemDefinition.WieldType;
import main.astraeus.utility.JsonLoader;

/**
 * Class that deserializes text from item_definitions.json and turns them into objects
 * for the server to use.
 * @author SeVen
 */
public class ItemDefinitionLoader extends JsonLoader {

	private static final Logger logger = Logger.getLogger(ItemDefinitionLoader.class.getName());
	
	public ItemDefinitionLoader() {
		super(Configuration.DATA + "json/item_definitions.json");
		load();
		logger.log(Level.INFO, "Loaded: " + ItemDefinition.DEFINITIONS.length + " Item Definitions.");
	}

	@Override
	public void load(JsonObject reader, Gson builder) {
			int id = reader.get("id").getAsInt();
			String name = Objects.requireNonNull(reader.get("name").getAsString());
			String desc = reader.get("description").getAsString();
			boolean tradeable = reader.get("tradeable").getAsBoolean();
			boolean destroyable = reader.get("destroyable").getAsBoolean();
			boolean stackable = reader.get("stackable").getAsBoolean();
			int value = reader.get("value").getAsInt();
			int specialPrice = reader.get("specialPrice").getAsInt();
			int lowAlch = reader.get("lowAlchemy").getAsInt();
			int highAlch = reader.get("highAlchemy").getAsInt();
			double weight = reader.get("weight").getAsDouble();
			boolean noted = reader.get("noted").getAsBoolean();
			boolean noteable = reader.get("noteable").getAsBoolean();
			int notedId = reader.get("notedId").getAsInt();
			int parentId = reader.get("parentId").getAsInt();
			boolean isTwoHanded = reader.get("isTwoHanded").getAsBoolean();
			WieldType type = builder.fromJson(reader.get("equipmentType"), WieldType.class);
			boolean weapon = reader.get("weapon").getAsBoolean();
			boolean fullBody = reader.get("fullBody").getAsBoolean();
			boolean fullHat = reader.get("fullHat").getAsBoolean();
			boolean fullMask = reader.get("fullMask").getAsBoolean();
			double[] bonuses = builder.fromJson(reader.get("bonus"), double[].class);

			String[] actions = builder.fromJson(reader.get("actions"), String[].class);
			ItemDefinition.DEFINITIONS[id] = new ItemDefinition(id, name, desc, tradeable, destroyable,
					stackable, value, specialPrice, lowAlch, highAlch, weight,
					noted, noteable, notedId, parentId, isTwoHanded, type, weapon, fullBody,
					fullHat, fullMask, bonuses, actions);
	}
	
}
