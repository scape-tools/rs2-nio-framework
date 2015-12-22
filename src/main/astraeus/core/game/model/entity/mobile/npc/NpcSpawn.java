package main.astraeus.core.game.model.entity.mobile.npc;

import main.astraeus.core.game.model.entity.Facing;
import main.astraeus.core.game.model.entity.Position;

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
	 * The position of this npc.
	 */
	private Position position;

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
	 * @param position
	 * 		The position of this npc. 
	 * 
	 * @param randomWalk
	 * 		The npc walks in a random direction.
	 */
	public NpcSpawn(int id, Position position, boolean randomWalk) {
		this(id, position, randomWalk, Facing.SOUTH);
	}

	/**
	 * Creates a new {@link NpcSpawn}.
	 * 
	 * @param id
	 * 		The id of the npc.
	 * 
	 * @param position
	 * 		The position of this npc. 
	 * 
	 * @param randomWalk
	 * 		The npc walks in a random direction.
	 * 
	 * @param facing
	 * 		The npcs facing direction.
	 */
	public NpcSpawn(int id, Position position, boolean randomWalk, Facing facing) {
		this.id = id;
		this.position = position;
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
	 * @return the position
	 */
	public Position getPosition() {
		return position;
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
