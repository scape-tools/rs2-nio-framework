package main.astraeus.game.model.entity;

import java.util.EnumMap;

import main.astraeus.game.model.Position;
import main.astraeus.game.model.entity.mobile.Movement;
import main.astraeus.game.model.entity.mobile.player.Player.Attributes;

public abstract class Entity {
      
      /**
       * The enumerated types of entities found in the game world.
       * 
       * @author SeVen
       */
      public enum Type {

        /**
         * The entity that resembles an item on the ground.
         */
        GROUND_ITEM,

        /**
         * The entity that resembles in-game items.
         */
        ITEM,

        /**
         * The entity that resembles a non-playable character.
         */
        NPC,

        /**
         * The entity that resembles in-game objects.
         */
        GAME_OBJECT,

        /**
         * The entity that resembles a player.
         */
        PLAYER;

      }
	
	/**
	 * The map of attributes for a player.
	 */
	protected EnumMap<Attributes, Object> attributes = new EnumMap<>(Attributes.class);	

	/**
	 * @return the attributes
	 */
	public EnumMap<Attributes, Object> getAttributes() {
		return attributes;
	}
	
	 /**
   * Gets the type of entity.
   */
  public abstract Type entityType();
	
	private transient int id;
	
	/**
	 * The index of an entity in an array.
	 */
	private int slot;

	/**
	 * The current coordinate point the entity is residing on.
	 */
	private Position position = new Position(0, 0, 0);
	
	/**
	 * Handles the movement procedures of an entity.
	 */
	private final Movement movement = new Movement(this);	

	/**
	 * Returns an instance of the currently registered {@link EntityEventListener}.
	 * 
	 * @return The returned instance. 
	 */
	public abstract EntityEventListener<? extends Entity> getEventListener();
		
	/**
	 * Returns the current coordinate point of the entity.
	 * 
	 * @return The returned coordinate point.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Modifies the current coordinate point of the entity.
	 * 
	 * @param position The new modification.
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	
	/**
	 * Returns the instance for entity movement control.
	 * 
	 * @return The returned instance.
	 */
	public final Movement getMovement() {
		return movement;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the slot
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * @param slot the slot to set
	 */
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
}