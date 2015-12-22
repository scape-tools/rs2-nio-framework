package main.astraeus.core.game.model.entity.item;

import main.astraeus.core.game.model.entity.Entity;
import main.astraeus.core.game.model.entity.EntityEventListener;

/**
 * Represents a single in-game entity that a player could potentially obtain.
 * 
 * @author SeVen
 */
public final class Item extends Entity {

	/**
	 * The id of this item.
	 */
	private final int id;

	/**
	 * The quantity of this item.
	 */
	private int amount;

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
	
	/**
	 * Sets the quantity of this item.
	 * 
	 * @param amount
	 * 		The amount to set.
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public EntityEventListener<? extends Entity> getEventListener() {
		return null;
	}
}