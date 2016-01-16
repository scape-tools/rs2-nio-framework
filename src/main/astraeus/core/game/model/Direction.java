package main.astraeus.core.game.model;

/**
 * Represents the enumerated directions an entity can walk or face.
 * 
 * @author SeVen
 */
public enum Direction {

	NORTH(0, 1, 1),

	NORTH_EAST(1, 1, 2),

	EAST(1, 0, 4),

	SOUTH_EAST(1, -1, 7),

	SOUTH(0, -1, 6),

	SOUTH_WEST(-1, -1, 5),

	WEST(-1, 0, 3),

	NORTH_WEST(-1, 1, 0),

	NONE(0, 0, -1);

	private final int directionX;

	private final int directionY;

	private final int id;

	/**
	 * Creates a {@link Direction}.
	 * 
	 * @param directionX
	 *            The value that represents a direction.
	 */
	Direction(int directionX, int directionY, int id) {
		this.directionX = directionX;
		this.directionY = directionY;
		this.id = id;
	}
	
	/**
	 * Gets the direction between two locations.
	 * 
	 * @param location
	 *            The location that will be the viewpoint.
	 * 
	 * @param other
	 *            The other location to get the direction of.
	 * 
	 * @return The direction of the other location.
	 */
	public static Direction getDirection(Position location, Position other) {

		final int deltaX = other.getX() - location.getX();
		final int deltaY = other.getY() - location.getY();

		if (deltaY >= 1) {
			if (deltaX >= 1) {
				return NORTH_EAST;
			} else if (deltaX == 0) {
				return NORTH;
			} else if (deltaX <= -1) {
				return NORTH_WEST;
			}
		} else if (deltaY == 0) {
			if (deltaX >= 1) {
				return Direction.EAST;
			} else if (deltaX == 0) {
				return Direction.NONE;
			} else if (deltaX <= -1) {
				return Direction.WEST;
			}
		} else if (deltaY <= -1) {
			if (deltaX >= 1) {
				return SOUTH_EAST;
			} else if (deltaX == 0) {
				return SOUTH;
			} else if (deltaX <= -1) {
				return SOUTH_WEST;
			}
		}

		return Direction.NONE;
	}

	/**
	 * @return the directionX
	 */
	public int getDirectionX() {
		return directionX;
	}

	/**
	 * @return the directionY
	 */
	public int getDirectionY() {
		return directionY;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

}
