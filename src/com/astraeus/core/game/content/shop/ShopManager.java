package com.astraeus.core.game.content.shop;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.astraeus.core.Configuration;
import com.astraeus.core.game.model.entity.item.Item;
import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.model.entity.mobile.player.Player.Attributes;
import com.astraeus.core.utility.ReadableState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * A class that contains static utility methods for shops.
 * 
 *  @author SeVen
 */
public class ShopManager implements ReadableState {
	
	/**
	 * The singleton for this class.
	 */
	private static ShopManager singleton;
	
	/**
	 * A collection of shops.
	 */
	private final static Set<Shop> shops = new HashSet<>();
	
	/**
	 * The amount of items that can be contained in a single store.
	 */
	public static final int SHOP_CAPACITY = 24;
	
	/**
	 * Opens a shop for a player.
	 * 
	 *  @param player
	 *  	The player to open the shop up for.
	 *  
	 *  @param shopId
	 *  	The id of the shop to open.
	 */
	public static final void openShop(Player player, int shopId) {
		 for(Shop shop : shops) {
			 if (shop.getId() == shopId) {
				 player.getAttributes().put(Attributes.SHOPPING, true);
				 player.getPacketSender().sendInventoryInterface(3824, 3822);
				 player.getInventoryContainer().update();
				 player.getPacketSender().sendString(shop.getTitle(), 3901);
				 player.getPacketSender().sendItemsOnInterface(3900, shop.getStock().getItems());
			 }
		 }
	}
	
	/**
	 * Gets the singletion for this class.
	 * 
	 * @return The singleton.
	 */
	public static ShopManager getSingleton() {
		
		if (singleton == null) {
			singleton = new ShopManager();
		}
		
		return singleton;
	}
	
	/**
	 * Gets the shops collection.
	 * 
	 * @return The instance of this collection.
	 */
	public Set<Shop> getShops() {
		return shops;
	}

	@Override
	public boolean deserialize() {
		try {
		final JsonParser fileParser = new JsonParser();
		final Object object = fileParser.parse(new FileReader(Configuration.DATA + "json/shops.json"));
		final Gson builder = new GsonBuilder().create();
		final JsonObject reader = (JsonObject) object;
		
		int id = reader.get("id").getAsInt();
		
		String title = Objects.requireNonNull(reader.get("title")).getAsString();
		
		Currency currency = Currency.valueOf(reader.get("currency").getAsString());
		
		//final ItemContainer container = new ShopInterfaceContainer(null);
		
		Item[] items = builder.fromJson(reader.get("items"), Item[].class);
		
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
