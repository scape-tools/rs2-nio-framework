package com.astraeus.core.game.model.entity.mobile;

import java.util.LinkedList;
import java.util.List;

import com.astraeus.core.game.model.entity.Entity;
import com.astraeus.core.game.model.entity.Position;
import com.astraeus.core.game.model.entity.UpdateFlags;
import com.astraeus.core.game.model.entity.mobile.player.Player;

public abstract class MobileEntity extends Entity {

	/**
	 * The entity's last known coordinate point.
	 */
	private Position lastPosition = new Position(0, 0, 0);

	/**
	 * An ordered collection or sequence of {@link UpdateFlags} which signify an update block.
	 */
	private final List<UpdateFlags> updateFlags = new LinkedList<UpdateFlags>();

	/**
	 * The players in the surrounding region of this entity.
	 */
	private final List<Player> localPlayers = new LinkedList<Player>();

	/**
	 * The direction the entity is walking.
	 */
	private int walkingDirection = -1;

	/**
	 * The direction the entity is running.
	 */
	private int runningDirection = -1;

	/**
	 * Returns an instance of the entity's last known coordinate point.
	 * 
	 * @return The returned instance.
	 */
	public Position getLastPosition() {
		return lastPosition;
	}

	/**
	 * Modifies the instance of the entity's last known coordinate point.
	 * 
	 * @param lastLocation The new modification.
	 */
	public void setLastPosition(Position lastLocation) {
		this.lastPosition = lastLocation;
	}

	/**
	 * Returns the collection of flags which signify an update block.
	 * 
	 * @return The returned collection.
	 */
	public List<UpdateFlags> getUpdateFlags() {
		return updateFlags;
	}

	/**
	 * Determines if an update block is required to be handled.
	 * 
	 * @return The determination.
	 */
	public boolean updateRequired() {
		return !updateFlags.isEmpty();
	}

	/**
	 * Returns the direction the entity is walking.
	 * 
	 * @return The returned direction.
	 */
	public int getWalkingDirection() {
		return walkingDirection;
	}

	/**
	 * Modifies the direction the entity is walking.
	 * 
	 * @param walkingDirection The new modification.
	 */
	public void setWalkingDirection(int walkingDirection) {
		this.walkingDirection = walkingDirection;
	}

	/**
	 * Returns the direction the entity is running.
	 * 
	 * @return The returned direction.
	 */
	public int getRunningDirection() {
		return runningDirection;
	}

	/**
	 * Modifies the direction the entity is running.
	 * 
	 * @param runningDirection The new modification.
	 */
	public void setRunningDirection(int runningDirection) {
		this.runningDirection = runningDirection;
	}

	/**
	 * Returns an ordered collection of players within the same region
	 * as this entity.
	 * 
	 * @return The returned collection.
	 */
	public List<Player> getLocalPlayers() {
		return localPlayers;
	}
}