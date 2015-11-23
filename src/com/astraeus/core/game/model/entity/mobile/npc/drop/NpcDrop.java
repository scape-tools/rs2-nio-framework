package com.astraeus.core.game.model.entity.mobile.npc.drop;

/**
 * A class which represents a single npc drop.
 * 
 * @author SeVen <https://github.com/7winds>
 */
public class NpcDrop {

	/**
	 * The item id for the item being dropped.
	 */
	private int itemId;
	/**
	 * The minimum quantity that can be dropped.
	 */
	private int minAmount;
	/**
	 * The maximum quantity that can be dropped.
	 */
	private int maxAmount;
	/**
	 * The chance for this drop.
	 */
	private Chance chance;

	/**
	 * A single npc drop
	 * @param itemId
	 * 		the item id for the item being dropped.
	 * @param chance
	 * 		the items chance for being dropped.
	 * @param minAmount
	 * 		the minimum quantity for this drop.
	 * @param maxAmount
	 * 		the maximum quantity for this drop.
	 */
	public NpcDrop(int itemId, Chance chance, int minAmount, int maxAmount) {
		this.itemId = itemId;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.chance = chance;
	}

	/**
	 * The item id of the item being dropped.
	 */
	public int getItemId() {
		return itemId;
	}
	
	/**
	 * Returns the minimum quantity that can be dropped for this item.
	 */
	public int getMinAmount() {
		return minAmount;
	}

	/**
	 * Sets the minimum quantity of an items drop.
	 * @param amount
	 * 		the amount or quantity of the item being dropped.
	 */
	public void setMinAmount(int amount) {
		this.minAmount = amount;
	}
	
	/**
	 * Returns the max quantity that can be dropped for this item.
	 */
	public int getMaxAmount() {
		return maxAmount;
	}

	/**
	 * Sets the maximum quantity of an items drop.
	 *  @param amount
	 *  	the amount or quantity of the item being dropped.
	 */
	public void setMaxAmount(int amount) {
		this.maxAmount = amount;
	}

	/**
	 * Determines the item being dropped is rare based on the ordinal of the chance.
	 */
	public boolean isFromRareTable() {
		if (chance.ordinal() >= Chance.RARE.ordinal()) {
			return true;
		}
		return false;
	}

	/**
	 * @return the chance
	 */
	public Chance getChance() {
		return chance;
	}
	
	/**
	 * Returns the difference between the minumum and maximum quantities.
	 */
	public int getExtraAmount() {
		return maxAmount - minAmount;
	}
	
}
