package main.astraeus.game.model;

public final class Position {
	
	/**
	 * The maximum distance a mobile entity can see.
	 */
	public static final int VIEWING_DISTANCE = 15;
	
	/**
	 * The number of height levels it takes to reach a new plane.
	 */
	public static final int HEIGHT_LEVELS = 4;

	/**
	 * The x coordinate on a grid.
	 */
	private int x;

	/**
	 * The y coordinate on a grid.
	 */
	private int y;

	/**
	 * The height of this point.
	 */
	private int height;
	
	/**
	 * Creates a new {@link Position} with a default
	 * {@code height} of {@code 0}.
	 * 
	 * @param x
	 * 		The x coordinate on a grid.
	 * @param y
	 * 		The y coordinate on a grid.
	 */
	public Position(int x, int y) {
		this(x, y, 0);
	}

	/**
	 * Creates a new {@link Position}.
	 * 
	 * @param x
	 * 		The x coordinate on a grid.
	 * @param y
	 * 		The y coordinate on a grid.
	 * @param height
	 * 		The height or plane.
	 */
	public Position(int x, int y, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
	}

	/**
	 * Gets the {@code x} coordinate.
	 * 
	 * @return The {@code x} coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets a new {@code x} value.
	 * 
	 * @param x
	 * 		The new value.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets a {@code y} coordinate.
	 * 
	 * @return The {@code y} coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets a new {@code y} coordinate.
	 *
	 * @param y
	 * 		The new value.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the {@code height level} on a plane.
	 * 
	 * @return The {@code height}.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets a new {@code height} value.
	 * 
	 * @param height
	 * 		The new value to set.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Returns the regional X coordinate.
	 * 
	 * @return The returned coordinate.
	 */
	public final int getRegionalX() {
		return (x >> 3) - 6;
	}

	/**
	 * Returns the regional Y coordinate.
	 * 
	 * @return The returned coordinate.
	 */
	public final int getRegionalY() {
		return (y >> 3) - 6;
	}

	/**
	 * Returns the local X coordinate of an {@link Position}.
	 * 
	 * @param location The {@link Position}.
	 * 
	 * @return The returned local coordinate.
	 */
	public final int getLocalX(Position location) {
		return x - 8 * location.getRegionalX();
	}

	/**
	 * Returns the local Y coordinate of an {@link Position}.
	 * 
	 * @param location The {@link Position}.
	 * 
	 * @return The returned local coordinate.
	 */
	public final int getLocalY(Position location) {
		return y - 8 * location.getRegionalY();
	}

	/**
	 * Modifies the current location with the coordinates of another.
	 * 
	 * @param other The new modification.
	 */
	public final void setPosition(Position other) {
		this.x = other.getX();
		this.y = other.getY();
		this.height = other.getHeight();
	}
	
	/**
	 * Adds an additional set of values onto the current position.
	 * 
	 * @param x The X coordinate value.
	 * 
	 * @param y The Y coordinate value.
	 * 
	 * @param plane The height plane value.
	 */
	public final void setPositionAdditional(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.height += z;
	}
	
	/**
	 * Performs a check to see if the specified position is equal to
	 * the instanced one.
	 * 
	 * @param other
	 * 		The other coordinate to be checked.
	 * 
	 * @return The result of the operation.
	 */
	public final boolean coordinatesEqual(Position other) {
		return other.getX() == this.getX() && other.getY() == this.getY() && other.getHeight() == this.getHeight();
	}

	/**
	 * Determines if another coordinate point is within a set distance
	 * of our own.
	 * 
	 * @param location The foreign coordinate point.
	 * 
	 * @param distance The distance.
	 * 
	 * @return The result of the operation. <code> true </code> or <code> false </code>.
	 */
	public final boolean isWithinDistance(Position location, int distance) {
		if (this.getHeight() != location.getHeight()) {
			return false;
		}
		return Math.abs(location.getX() - this.getX()) <= distance && Math.abs(location.getY() - this.getY()) <= distance;
	}

	@Override
	public String toString() {
		return x + " - " + y + " - " + height;
	}
}