package com.astraeus.core.game.utility;

public class Utilities {
	
	/**
	 * Converts a string into a long value. This is used for
	 * the player appearance updating to update a players username.
	 * 
	 * @param string
	 * 		The string to convert. 	
	 */
	public static long convertStringToLong(String string) {
		long l = 0L;
		int i = 0;
		do {
			char c = string.charAt(i);
			l *= 37L;
			if ((c >= 'A') && (c <= 'Z')) {
				l += '\001' + c - 65;
			} else if ((c >= 'a') && (c <= 'z')) {
				l += '\001' + c - 97;
			} else if ((c >= '0') && (c <= '9')) {
				l += '\033' + c - 48;
			}
			i++;
			if (i >= string.length()) {
				break;
			}
		} while (i < 12);
		while ((l % 37L == 0L) && (l != 0L)) {
			l /= 37L;
		}
		return l;
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
