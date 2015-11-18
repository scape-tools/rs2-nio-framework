package com.runescape.core.game.model.entity;

public final class Position {

	/**
	 * The X coordinate of this point.
	 */
	private int x;

	/**
	 * The Y coordinate of this point.
	 */
	private int y;

	/**
	 * The height of this point.
	 */
	private int z;
	
	/**
	 * The Position of this entity, with a default height of 0.
	 * 
	 * @param x
	 * 		The X coordinate of this point.
	 * @param y
	 * 		The Y coordinate of this point.
	 */
	public Position(int x, int y) {
		this(x, y, 0);
	}

	/**
	 * The Position of this entity.
	 * 
	 * @param x The X coordinate of this point.
	 * 
	 * @param y The Y coordinate of this point.
	 * 
	 * @param z The height of this point.
	 */
	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns the X coordinate of this point.
	 * 
	 * @return The returned coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Modifies the X coordinate of this point.
	 * 
	 * @param x The new modification.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the Y coordinate of this point.
	 * 
	 * @return The returned coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Modifies the Y coordinate of this point.
	 * 
	 * @param y The new modification.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the dimensional plane of this coordinate.
	 * 
	 * @return The returned plane.
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Modifies the dimensional plane of this coordinate.
	 * 
	 * @param plane The new modification.
	 */
	public void setZ(int plane) {
		this.z = plane;
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
	public final void setLocation(Position other) {
		this.x = other.getX();
		this.y = other.getY();
		this.z = other.getZ();
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
		this.z += z;
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
		if (this.getZ() != location.getZ()) {
			return false;
		}
		return Math.abs(location.getX() - this.getX()) <= distance && Math.abs(location.getY() - this.getY()) <= distance;
	}

	@Override
	public String toString() {
		return x + " - " + y + " - " + z;
	}
}