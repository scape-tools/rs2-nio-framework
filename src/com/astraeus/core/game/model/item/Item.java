package com.astraeus.core.game.model.item;

/**
 * Represents a single in-game entity that a player could potentially obtain.
 * 
 * @author SeVen
 */
public final class Item {

	/**
	 * The id of this item.
	 */
	private final int id;

	/**
	 * The quantity of this item.
	 */
	private final int amount;

	/**
	 * The {@link Item} Constructor used to instantiate this class.
	 * 
	 * @param id
	 * 		The unique identification of this item.
	 * 
	 * @param amount
	 * 		The quantity of this item.
	 */
	public Item(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}

	/**
	 * Returns the numerical identification index for this item.
	 * 
	 * @return The returned index.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the quantity of this item.
	 * 
	 * @return The quantity.
	 */
	public int getAmount() {
		return amount;
	}
}