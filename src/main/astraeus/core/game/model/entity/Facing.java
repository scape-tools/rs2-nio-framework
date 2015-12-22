package main.astraeus.core.game.model.entity;

/**
 * Represents an entities facing direction.
 * 
 * @author SeVen
 */
public enum Facing {
	
	NORTH(-1),
	
	EAST(0),
	
	SOUTH(1),
	
	WEST(2);
	
	private int direction;
	
	private Facing(int direction) {
		this.direction = direction;
	}

	/**
	 * @return the direction
	 */
	public int getDirection() {
		return direction;
	}
}
