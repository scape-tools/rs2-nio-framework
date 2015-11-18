package com.astraeus.core.game.model.entity.mobile.player;

/**
 * @author Dylan Vicchiarelli
 *
 * Represents a point an entity is moving towards.
 */
public final class MovementPoint {

	/**
	 * The X coordinate of the point.
	 */
	private final int x;

	/**
	 * The Y coordinate of the point.
	 */
	private final int y;

	/**
	 * The direction of the point corresponding to the player.
	 */
	private final int direction;

	/**
	 * The default class constructor.
	 * 
	 * @param x The X coordinate of the point.
	 * 
	 * @param y The Y coordinate of the point.
	 * 
	 * @param direction The corresponding direction.
	 */
	public MovementPoint(int x, int y, int direction) {

		this.x = x;

		this.y = y;

		this.direction = direction;
	}

	/**
	 * Returns the X coordinate of the point.
	 * 
	 * @return The returned coordinate.
	 */
	public final int getX() {

		return x;
	}

	/**
	 * Returns the Y coordinate of the point.
	 * 
	 * @return The returned coordinate.
	 */
	public final int getY() {

		return y;
	}

	/**
	 * Returns the corresponding direction of the point.
	 * 
	 * @return The returned direction.
	 */
	public final int getDirection() {

		return direction;
	}
}
