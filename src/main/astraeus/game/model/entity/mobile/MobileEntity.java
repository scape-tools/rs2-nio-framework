package main.astraeus.game.model.entity.mobile;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import main.astraeus.game.model.Animation;
import main.astraeus.game.model.ForceMovement;
import main.astraeus.game.model.Graphic;
import main.astraeus.game.model.Hit;
import main.astraeus.game.model.Position;
import main.astraeus.game.model.entity.Entity;
import main.astraeus.game.model.entity.mobile.npc.Npc;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.player.Player.Attributes;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;

public abstract class MobileEntity extends Entity {

	/**
	 * The entity's last known coordinate point.
	 */
	private Position lastPosition = new Position(0, 0, 0);

	/**
	 * The update flags held by the entity.
	 */
	private UpdateFlags updateFlags = new UpdateFlags();
	private final Queue<Animation> animations = new PriorityQueue<>();
	private final Queue<Graphic> graphics = new PriorityQueue<>();	
	private Hit primaryHit;	
	private Hit secondaryHit;
	
	private ForceMovement forceMovement;
	
	/**
	 * The players in the surrounding region of this entity.
	 */
	private final List<Player> localPlayers = new LinkedList<Player>();
	
	private boolean registered = false;
	
	private String forcedChat = "";
	
	/**
	 * The entity interacting with the current entity
	 */
	private transient Entity interactingEntity;

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
	
	public void startAnimation(final Animation animation) {
		if (animation != null) {
			animations.add(animation);
			getUpdateFlags().flag(UpdateFlag.ANIMATION);
		}
	}
	
	public Animation getAnimation() {
		return animations.peek() == null ? new Animation(65535) : animations.peek();
	}
	
	public Queue<Animation> getAnimations() {
		return animations;
	}
	
	public void startGraphic(Graphic graphic) {
		if (graphic != null) {
			graphics.add(graphic);
			getUpdateFlags().flag(UpdateFlag.GRAPHICS);
		}
	}
	
	public Graphic getGraphic() {
		return graphics.peek() == null ? new Graphic(65535) : graphics.peek();
	}
	
	public Queue<Graphic> getGraphics() {
		return graphics;
	}
	
	public Hit getPrimaryHit() {
		return primaryHit;
	}
	
	public Hit getSecondaryHit() {
		return secondaryHit;
	}
	
	/**
	 * @return the forceMovement
	 */
	public ForceMovement getForceMovement() {
		return forceMovement;
	}
	
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

	/**
	 * @return the forcedChat
	 */
	public String getForcedChat() {
		return forcedChat;
	}

	/**
	 * @param forcedChat the forcedChat to set
	 */
	public void setForcedChat(String forcedChat) {
		this.forcedChat = forcedChat;
	}
	
	/**
	 * Sets the interacting entity
	 * 
	 * @param entity
	 */
	public void setInteractingEntity(Entity entity) {
		this.interactingEntity = entity;
		getUpdateFlags().flag(UpdateFlag.ENTITY_INTERACTION);
	}
	
	/**
	 * Returns the entity interacting with the current entity
	 * 
	 * @return
	 */
	public Entity getInteractingEntity() {
		return interactingEntity;
	}
	
	public boolean isNpc() {		
		return this instanceof Npc ? true : false;
	}
	
	public boolean isPlayer() {
		return this instanceof Player ? true : false;
	}
	
}