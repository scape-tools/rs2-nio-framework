package main.astraeus.core.game.model;

/**
 * Represents the enumerated directions an entity can walk or face.
 * 
 * @author SeVen
 */
public enum Direction {
	
	NORTH(1),
	
	NORTH_EAST(2),
	
	EAST(4),
	
	SOUTH_EAST(7),

	SOUTH(6),
	
	SOUTH_WEST(5),
	
	WEST(3),
	
	NORTH_WEST(0),
	
	NONE(-1);
	
	private final int direction;
	
	Direction(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}

}
