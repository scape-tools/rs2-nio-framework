package main.astraeus.game.model.entity.object;

import main.astraeus.game.model.Direction;
import main.astraeus.game.model.Position;
import main.astraeus.game.model.entity.Entity;
import main.astraeus.game.model.entity.EntityEventListener;

/**
 * Represents an object that can be spawned into the game world.
 * 
 * @author SeVen
 */
public class GameObject extends Entity {

      /**
       * The id of this object.
       */
      private final int id;

      /**
       * The type of this object.
       */
      private final GameObjectType type;

      /**
       * The location of this object.
       */
      private final Position location;

      /**
       * The orientation of this object.
       */
      private final Direction orientation;

      /**
       * Creates a new {@link GameObject}, with a default type of {@code 10}.
       * 
       * @param id The id of this object.
       * 
       * @param location The location of this object.
       */
      public GameObject(int id, Position location) {
            this(id, GameObjectType.INTERACTABLE, location, Direction.SOUTH);
      }

      /**
       * Creates a new {@link GameObject}, with a default type of {@code 10}.
       * 
       * @param id The id of this object.
       * 
       * @param location The location of this object.
       * 
       * @param orientation The facing direction of this object.
       */
      public GameObject(int id, Position location, Direction orientation) {
            this(id, GameObjectType.INTERACTABLE, location, orientation);
      }

      /**
       * Creates a new {@link GameObject}.
       * 
       * @param id The id of this object.
       * 
       * @param type The type of this object.
       * 
       * @param location The location of this object.
       * 
       * @param orientation The facing direction of this object.
       */
      public GameObject(int id, GameObjectType type, Position location, Direction orientation) {
            this.id = id;
            this.type = type;
            this.location = location;
            this.orientation = orientation;
      }

      /**
       * @return the id
       */
      public int getId() {
            return id;
      }

      /**
       * @return the location
       */
      @Override
      public Position getPosition() {
            return location;
      }

      /**
       * The facing direction of this object.
       * 
       * @return The orientation
       */
      public Direction getOrientation() {
            return orientation;
      }

      /**
       * @return the type
       */
      public GameObjectType getType() {
            return type;
      }
      
      @Override
      public String toString() {
            return String.format("[ID= %d], [POS= %s], [TYPE= %s]", id, location.toString(), entityType().name());
      }

      @Override
      public Type entityType() {
            return Type.GAME_OBJECT;
      }

      @Override
      public EntityEventListener<? extends Entity> getEventListener() {
            return null;
      }

}
