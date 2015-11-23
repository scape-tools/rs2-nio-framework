package com.astraeus.core.game.model.entity.mobile;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.IntStream;

import com.astraeus.core.Server;
import com.astraeus.core.game.model.entity.mobile.player.Player;

/**
 * A collection that provides functions for storing and manipulating entities. 
 * This list does not support the storage of elements with a value of
 * {@code null}, and maintains an extremely strict ordering of the elements.
 * This list for storing characters will be much faster than typical
 * implementations, mainly due to the fact that it uses a {@link Queue} to cache
 * available slots in order to prevent expensive lookups needed to add a new
 * character.
 * 
 * @author lare96 <http://github.com/lare96>
 * 
 * @author SeVen (Modified version)
 * 
 * @param <E>
 *            the type of character being managed with this collection.
 */
public class EntityList<E extends MobileEntity> {

	/**
	 * The array of {@link MobileEntity}s.
	 */
	private E[] entities;

    /**
     * The queue containing all of the cached slots that can be assigned to
     * {@link MobileEntity}s to prevent expensive lookups.
     */
	private Queue<Integer> slotQueue = new ArrayDeque<>();
	
	/**
	 * The capacity of this collection.
	 */
	private final int capacity;	
	
	/**
	 * The size of this collection.
	 */
	private int size;
	
	@SuppressWarnings("unchecked")
	public EntityList(int capacity) {
		this.capacity = capacity;
		this.entities = (E[]) new MobileEntity[capacity];
		this.size = 0;
		IntStream.rangeClosed(1, capacity).forEach(slotQueue::add);
	}
	
    /**
     * Adds an element to this collection.
     *
     * @param e
     *            the element to add to this collection.
     * @return {@code true} if the element was successfully added, {@code false}
     *         otherwise.
     */
	public boolean add(E e) {
		Objects.requireNonNull(e);
		
		if(!e.isRegistered()) {
		int slot = slotQueue.remove();
		e.setRegistered(true);
		System.out.println("assigning slot: " + slot);
		e.setIndex(slot);
		entities[slot] = e;
		if (e instanceof Player) {
		Server.getUpdateProcessor().getPlayers().put(e.getIndex(), (Player) e);
		}
		size++;
		System.out.println("Size after adding: " + size);
		return true;
		}
		return false;
	}
	
    /**
     * Removes an element from this collection.
     *
     * @param e
     *            the element to remove from this collection.
     * @return {@code true} if the element was successfully removed,
     *         {@code false} otherwise.
     */
	public boolean remove(E e) {
		Objects.requireNonNull(e);
		if(e.isRegistered() && entities[e.getIndex()] != null) {
			e.setRegistered(false);
			e.dispose();
			entities[e.getIndex()] = null;
			slotQueue.add(e.getIndex());
			size--;
			System.out.println("Size after removing: " + size);
			return true;
		}
		return false;
	}

	/**
	 * @return the entities
	 */
	public E[] getEntities() {
		return entities;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	
}
