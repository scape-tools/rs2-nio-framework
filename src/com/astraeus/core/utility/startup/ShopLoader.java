package com.astraeus.core.utility.startup;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.astraeus.core.Configuration;
import com.astraeus.core.game.content.shop.Currency;
import com.astraeus.core.game.content.shop.Shop;
import com.astraeus.core.game.content.shop.ShopManager;
import com.astraeus.core.game.model.entity.item.Item;
import com.astraeus.core.utility.JsonLoader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * The class that initializes shops on startup.
 * 
 * @author SeVen
 */
public class ShopLoader extends JsonLoader {

	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(ShopLoader.class.getName());
	
	public ShopLoader() {
		super(Configuration.DATA + "json/shops.json");
		load();
		logger.log(Level.INFO, String.format("Loaded: %d shops.", ShopManager.shops.size()));
	}

	@Override
	public void load(JsonObject reader, Gson builder) {
		int id = reader.get("id").getAsInt();
		
		String title = Objects.requireNonNull(reader.get("title")).getAsString();
		
		Currency currency = Currency.valueOf(reader.get("currency").getAsString());
				
		Item[] items = builder.fromJson(reader.get("items"), Item[].class);
		
		Shop shop = new Shop(id, title, currency, items);
		
		shop.getStock().setItems(items);
		
		ShopManager.shops.add(shop);
	}

}
