package main.astraeus.game.model.entity.mobile.player;

/**
 * An enumeration of the type of rights for a player.
 * 
 * @author SeVen
 */
public enum PlayerRights {

      /**
       * A normal player.
       */
      PLAYER(0, 0),

      /**
       * A player moderator.
       */
      MODERATOR(1, 1),

      /**
       * An admin of the server.
       */
      ADMINISTRATOR(2, 2),

      /**
       * A developer for the server.
       */
      DEVELOPER(2, 3);

      private final int protocolValue;

      private final int value;

      private PlayerRights(int protocolValue, int value) {
            this.protocolValue = protocolValue;
            this.value = value;
      }

      public boolean greater(PlayerRights other) {
            return this.ordinal() > other.ordinal();
      }

      public boolean greaterOrEqual(PlayerRights other) {
            return this.ordinal() >= other.ordinal();
      }

      public boolean less(PlayerRights other) {
            return this.ordinal() < other.ordinal();
      }

      public boolean lessOrEqual(PlayerRights other) {
            return this.ordinal() <= other.ordinal();
      }

      public boolean equal(PlayerRights other) {
            return this.ordinal() <= other.ordinal();
      }

      public int getProtocolValue() {
            return protocolValue;
      }

      public int getValue() {
            return value;
      }

}
