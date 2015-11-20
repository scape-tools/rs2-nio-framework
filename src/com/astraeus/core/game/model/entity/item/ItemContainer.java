package com.astraeus.core.game.model.entity.item;

import com.astraeus.core.game.model.entity.mobile.player.Player;

/**
 * A container that represents a collection of items.
 * 
 * @author SeVen
 */
public abstract class ItemContainer {
	
	/**
	 * The enumerated types that represent an items ability to stack.
	 * 
	 * @author SeVen
	 */
	public enum StackType {
		
		/**
		 * Items will stack or not stack depending on their definitions.
		 */
		NORMAL,
		
		/**
		 * Items in the container will always stack.
		 */
		ALWAYS_STACK,
		
		/**
		 * Items in the container will never stack.
		 */
		NEVER_STACK;
		
	}
	
	/**
	 * The size of this container.
	 */
	private final int size;
	
	/**
	 * The items of this container.
	 */
	private Item[] items;
	
	/**
	 * The type of stack for this container.
	 */
	private StackType stackType;
	
	/**
	 * Constructs a new {@link ItemContainer} with a default
	 * {@link StackType} of {@code NORMAL}.
	 * 
	 * @param player
	 * 		The player that owns this container.
	 * 
	 * @param size
	 * 		The size of this container.
	 */
	public ItemContainer(int size) {
		this(size, StackType.NORMAL);
	}
	
	/**
	 * Constructs a new {@link ItemContainer}.
	 * 
	 * @param player
	 * 		The player that owns this container.
	 * 
	 * @param size
	 * 		The size of this container.
	 */
	public ItemContainer(int size, StackType stackType) {
		this.size = size;
		this.items = new Item[size];
		this.stackType = stackType;		
	}
	
	/**
	 * Adds a specified item into a container.
	 * 
	 * @param item
	 * 		The item to add.
	 */
	public abstract void addItem(Item item);
	
	/**
	 * Removes an item from a container.
	 * 
	 * @param id
	 * 		The id of the item to remove.
	 * 
	 * @param amount
	 * 		The amount to remove.
	 */
	public abstract void removeItem(int id, int amount);
	
	/**
	 * Updates a container.
	 */
	public abstract void update();
	
	/**
	 * Checks to see if a container can hold a specified item.
	 * 
	 * @param id
	 * 		The id of the item to check.
	 * 
	 * {@code true} If there is enough space, {@code false} otherwise.
	 */
	public final boolean canHoldItem(Player player, int id) {
		
		if (getFreeSlots() > 0) {
			return true;
		}

		if (player.getInventoryContainer().containsItem(id) && ItemDefinition.getDefinitions()[id].isStackable()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks to see if a container contains a specified item.
	 * 
	 * @param itemId
	 * 		The id of the item to check.
	 * 
	 * @return {@code true} If the container contains this item. {@code false} otherwise.
	 */
	public final boolean containsItem(int itemId) {
		for (int index = 0; index < getSize(); index ++) {
			
			if (getItems()[index] == null) {
				continue;
			}

			if (getItems()[index].getId() == itemId) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Counts the number of free slots available in a container.
	 * 
	 * @return The amount of free space.
	 */
	public final int getFreeSlots() {
		int count = 0;
		for (int index = 0; index < getSize(); index ++) {
			if (getItems()[index] == null) {
				count ++;
			}
		}
		return count;
	}

	/**
	 * @return the items
	 */
	public Item[] getItems() {
		return items;
	}
	
	/**
	 * Sets the items for this container.
	 * 
	 * @param items
	 * 		The array of items for this container.
	 */
	public final void setItems(Item[] items) {
		this.items = items;
	}
	
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * @return the stackType
	 */
	public StackType getStackType() {
		return stackType;
	}
	
}
