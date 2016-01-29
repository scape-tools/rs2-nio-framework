package main.astraeus.utility;

/**
 * The static-utility class that contains string-related methods.
 * 
 * @author SeVen
 */
public class StringUtils {

      public static final String capitalizePlayerName(String name) {
            String capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1);
            return capitalizedName;
      }

}
