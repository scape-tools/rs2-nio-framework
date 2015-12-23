package main.astraeus.core.game.model.entity.mobile;

import java.util.LinkedList;
import java.util.List;

import main.astraeus.core.game.model.entity.Entity;
import main.astraeus.core.game.model.entity.Position;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.Player.Attributes;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;

public abstract class Character extends Entity {

	/**
	 * The entity's last known coordinate point.
	 */
	private Position lastPosition = new Position(0, 0, 0);

	/**
	 * The update flags held by the entity.
	 */
	private UpdateFlags updateFlags = new UpdateFlags();
	
	/**
	 * The players in the surrounding region of this entity.
	 */
	private final List<Player> localPlayers = new LinkedList<Player>();
	
	private boolean registered = false;

	/**
	 * The direction the entity is walking.
	 */
	private int walkingDirection = -1;

	/**
	 * The direction the entity is running.
	 */
	private int runningDirection = -1;
	
	public abstract int getCurrentHealth();

	public abstract void dispose();
	
	/**
	 * Faces a coordinate point.
	 * 
	 * @param coordinate The point to be faced.
	 */
	public final void faceDirection(Position position) {
		attributes.put(Attributes.FACE_COORDINATE, position);
		updateFlags.flag(UpdateFlag.FACE_COORDINATE);
	}
	
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
	
	/**
	 * @return the updateFlags
	 */
	public UpdateFlags getUpdateFlags() {
		return updateFlags;
	}
	
	/**
	 * @return the registered
	 */
	public boolean isRegistered() {
		return registered;
	}

	/**
	 * @param registered the registered to set
	 */
	public void setRegistered(boolean registered) {
		this.registered = registered;
	}
}