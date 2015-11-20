package com.astraeus.core.game.content.shop;

import java.util.HashSet;
import java.util.Set;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.model.entity.mobile.player.Player.Attributes;

/**
 * A class that contains static utility methods for shops.
 * 
 *  @author SeVen
 */
public class ShopManager {
	
	/**
	 * The singleton for this class.
	 */
	private static ShopManager singleton;
	
	/**
	 * A collection of shops.
	 */
	public final static Set<Shop> shops = new HashSet<>();
	
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
	
}
