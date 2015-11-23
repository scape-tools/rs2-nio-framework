package com.astraeus.core.game.model.entity.mobile.npc;

import com.astraeus.core.game.model.entity.Facing;

/**
 * A class which represents a single npc spawn in the virtual world.
 * 
 * @author SeVen
 */
public final class NpcSpawn {

	/**
	 * The id of this npc. 
	 */
	private int id;
	
	/**
	 * The x coordinate of this npc.
	 */
	private int x;

	/**
	 * The y coordinate of this npc.
	 */
	private int y;

	/**
	 * The height of this npc on a plane.
	 */
	private int height;

	/**
	 * The Walking type for the npc.
	 */
	private Facing facing;
	
	/**
	 * The npcs ability to walk in random directions.
	 */
	private boolean randomWalk;
	
	/**
	 * Creates a new {@link NpcSpawn} with a default
	 * {@link Facing} value of {@code SOUTH}.
	 * 
	 * @param id
	 * 		The id of the npc.
	 * 
	 * @param x
	 * 		The x coordinate of this npc.
	 * 
	 * @param y
	 * 		The y coordinate of this npc.
	 * 
	 * @param height
	 * 		The height of this npc on a plane.
	 * 
	 * @param randomWalk
	 * 		The npc walks in a random direction.
	 */
	public NpcSpawn(int id, int x, int y, int height, boolean randomWalk) {
		this(id, x, y, height, randomWalk, Facing.SOUTH);
	}

	/**
	 * Creates a new {@link NpcSpawn}.
	 * 
	 * @param id
	 * 		The id of the npc.
	 * 
	 * @param x
	 * 		The x coordinate of this npc.
	 * 
	 * @param y
	 * 		The y coordinate of this npc.
	 * 
	 * @param height
	 * 		The height of this npc on a plane.
	 * 
	 * @param randomWalk
	 * 		The npc walks in a random direction.
	 * 
	 * @param facing
	 * 		The npcs facing direction.
	 */
	public NpcSpawn(int id, int x, int y, int height, boolean randomWalk, Facing facing) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.height = height;
		this.randomWalk = randomWalk;
		this.facing = facing;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the facing
	 */
	public Facing getFacing() {
		return facing;
	}

	/**
	 * @return the randomWalk
	 */
	public boolean isRandomWalk() {
		return randomWalk;
	}

}
