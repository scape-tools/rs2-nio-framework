package com.runescape.core.game.utility;

public class Utilities {
	
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
