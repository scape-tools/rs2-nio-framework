package com.astraeus.core.game.content.shop;

import com.astraeus.core.game.content.shop.Currency;
import com.astraeus.core.game.model.entity.item.Item;
import com.astraeus.core.game.model.entity.item.ItemContainer;
import com.astraeus.core.game.model.entity.item.container.ShopContainer;

/**
 * Represents a single in-game shop.
 * 
 * @author SeVen
 */
public final class Shop {

	/**
	 * The id of the shop.
	 */
	private final int id;
	
	/**
	 * The currency of this shop.
	 */
	private final Currency currency;
	
	/**
	 * The items present in the shop.
	 */
	private final Item[] items;

	/**
	 * The current stock of the shop.
	 */
	private final ItemContainer stock = new ShopContainer(null);

	/**
	 * The title displayed on the top bar of the shop interface.
	 */
	private final String title;
	
	/**
	 * Constructs a new {@link Shop} with a default
	 * {@link Currency} of {@code COINS}.
	 * 
	 * @param id
	 * 		The id number of this shop.
	 * 
	 * @param title
	 * 		The title of the shop.
	 *  
	 * @param items
	 * 		The items present in the shop.
	 */
	public Shop(int id, String title, Item[] items) {
		this(id, title, Currency.COINS, items);
	}

	/**
	 * Constructs a new {@link Shop}.
	 * 
	 * @param id
	 * 		The id number of this shop.
	 * 
	 * @param title
	 * 		The title of the shop.
	 * 
	 * @param currency
	 * 		The currency of this shop.
	 * 
	 * @param items
	 * 		The items present in the shop.
	 * 
	 */
	public Shop(int id, String title, Currency currency, Item[] items) {
		this.id = id;
		this.currency = currency;
		this.items = items;
		this.title = title;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * @return the stock
	 */
	public ItemContainer getStock() {
		return stock;
	}

	/**
	 * @return the items
	 */
	public Item[] getItems() {
		return items;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

}
