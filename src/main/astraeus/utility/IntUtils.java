package main.astraeus.utility;

/**
 * The static-utility class that contains integer-related methods.
 * 
 * @author SeVen
 */
public class IntUtils {

      public static <T> int findFreeIndex(T[] t) {
            for (int index = 0; index < t.length; index++) {
                  if (t[index] == null) {
                        return index;
                  }
            }
            return -1;
      }

      public static int parseDirection(int deltaX, int deltaY) {
            if (deltaX < 0) {
                  if (deltaY < 0) {
                        return 5;
                  }
                  if (deltaY > 0) {
                        return 0;
                  }
                  return 3;
            }
            if (deltaX > 0) {
                  if (deltaY < 0) {
                        return 7;
                  }
                  if (deltaY > 0) {
                        return 2;
                  }
                  return 4;
            }
            if (deltaY < 0) {
                  return 6;
            }
            if (deltaY > 0) {
                  return 1;
            }
            return -1;
      }

}
