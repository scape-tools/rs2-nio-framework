package main.astraeus.game.model.entity.object;

import java.util.Optional;

public enum GameObjectType {
      STRAIGHT_WALL(0),
      DIAGONAL_WALL(1),
      CORNER(2),
      STRAIGHT_WALL_CORNER(3),
      STRAIGHT_INSIDE_WALL_DECORATION(4),
      STRAIGHT_OUTSIDE_WALL_DECORATION(5),
      DIAGONAL_OUTSIDE_WALL_DECORATION(6),
      DIAGONAL_INSIDE_WALL_DECORATION(7),
      DIAGONAL_IN_WALL_DECORATION(8),
      DIAGONAL_WALLS(9),
      INTERACTABLE(10),
      GROUND_OBJECT(11),
      STRAIGHT_SLOPED_ROOF(12),
      DIAGONAL_SLOPED_ROOFS(13),
      DIAGONAL_SLOPED_CONNECTING_ROOF(14),
      STRAIGHT_SLOPED_CORNER_CONNECTING_ROOF(15),
      STRAIGHT_SLOPED_CORNER_ROOF(16),
      STRAIGHT_FLAT_ROOF(17),
      STRAIGHT_BOTTOM_EDGE_ROOF(18),
      DIAGONAL_BOTTOM_EDGE_CONNECTING_ROOF(19),
      STRAIGHT_BOTTOM_EDGE_CONNECTING_ROOF(20),
      STRAIGHT_BOTTOM_EDGE_CONNECTING_CORNER_ROOF(21),
      GROUND_DECORATION(22);

      /**
       * The integer value of this ObjectType.
       */
      private final int value;

      /**
       * Creates the ObjectType.
       *
       * @param value
       *            The integer value of this ObjectType.
       * @param group
       *            The ObjectGroup of this type.
       */
      private GameObjectType(int value) {
        this.value = value;
      }

      /**
       * Gets the integer value of this ObjectType.
       *
       * @return The value.
       */
      public int getValue() {
        return value;
      }
      
      /**
       * Gets the type of an object based on its id.
       * 
       * @param id
       *    The id of the object to lookup.
       */
      public Optional<GameObjectType> lookup(int id) {
        for (GameObjectType type : values()) {
          if (type.value == id) {
            return Optional.of(type);
          }
        }        
        return Optional.empty();
      }

    }
