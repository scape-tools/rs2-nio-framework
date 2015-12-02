package com.astraeus.core.game.model.entity;

/**
 * The container that creates an array of entities.
 * 
 * @author SeVen
 */
public final class EntityList<E extends Entity> {

	/**
	 * The array of entities.
	 */
	private E[]indexes;

	/**
	 * The capacity of this container.
	 */
	private final int capacity;
	
	/**
	 * The size of this container.
	 */
	private int size;

	/**
	 * Creates a new {@link EntityList).
	 * 
	 * @param capacity
	 * 		The amount of entities that can be in this container.
	 */
	@SuppressWarnings("unchecked")
	public EntityList(int capacity) {
		this.capacity = capacity;
		this.indexes = (E[]) new Entity[capacity];
	}
	
	/**
	 * Adds an entity to this container.
	 */
	public void add(E e) {
		int slot = calculateIndex();
		if(slot != -1) {
			indexes[slot] = e;
			size++;
		}
	}
	
	/**
	 * Gets an available free index for an entity.
	 */
	public final int calculateIndex() {

		for(int index = 0; index < indexes.length; index++) {
			if(indexes[index] == null) {
				return index;
			}
		}
		
		return -1;
	}
	
	/**
	 * Removes an entity from the array, by setting the index to null.
	 * 
	 * @param index
	 * 		The index to remove.
	 */
	public final void removeIndex(int index){
		getIndexes()[index] = null;
		size--;
	}
	
	/**
	 * Gets an index of entities.
	 */
	public final E[] getIndexes() {
		return indexes;
	}
	
	/**
	 * Gets the capacity of this container.
	 */
	public final int getCapacity() {
		return capacity;
	}

	/**
	 * Gets the size of this container.
	 */
	public int getSize() {
		return size;
	}
	
}
