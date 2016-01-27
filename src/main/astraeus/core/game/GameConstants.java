package main.astraeus.core.game;

/**
 * Class containing game-related constants.
 * 
 * @author SeVen
 */
public class GameConstants {
      
      /**
       * The rate in milliseconds in which the game processes logic.
       */
      public static final int CYCLE_RATE = 600;

      /**
       * The number of items in a 474 deob.
       */
      public static final int MAXIMUM_ITEMS = 11813;

      /**
       * The number of npcs in a 474 deob.
       */
      public static final int MAX_LISTED_NPCS = 6102;

      /**
       * The directions for pedestrian X coordinate movement.
       */
      public static final byte[] DIRECTION_DELTA_X = new byte[] {-1, 0, 1, -1, 1, -1, 0, 1};

      /**
       * The directions for pedestrian Y coordinate movement.
       */
      public static final byte[] DIRECTION_DELTA_Y = new byte[] {1, 1, 1, 0, 0, -1, -1, -1};

      /**
       * The side-bar interfaces for the player's game-frame.
       */
      public static final int[] SIDE_BARS =
                  {2423, 3917, 638, 3213, 1644, 5608, 1151, -1, 5065, 5715, 2449, 904, 147, 962};


      public static final int ATTACK_TAB = 0, SKILLS_TAB = 1, QUESTS_TAB = 2, INVENTORY_TAB = 3,
                  EQUIPMENT_TAB = 4, PRAYER_TAB = 5, MAGIC_TAB = 6, CLAN_CHAT_TAB = 7,
                  FRIEND_TAB = 8, IGNORE_TAB = 9, LOGOUT = 10, OPTIONS_TAB = 11, EMOTES_TAB = 12,
                  MUSIC_TAB = 13;
}
