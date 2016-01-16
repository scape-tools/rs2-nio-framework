package main.astraeus.core.game.model;

/**
 * Represents the force movement mask that will force a {@link Player} to move
 * to a certain destination.
 */
public class ForceMovement {

	/**
	 * The starting location
	 */
	private final Position startLocation;

	/**
	 * The ending location
	 */
	private final Position endLocation;

	/**
	 * The time in ticks that it will take to move across the {@code X} axis.
	 */
	private final int durationX;

	/**
	 * The time in ticks that it will take to move across the {@code Y} axis.
	 */
	private final int durationY;

	/**
	 * The {@link Direction} that the {@link Player} is moving in.
	 */
	private final Direction direction;

	public ForceMovement(Position startPosition, Position endPosition, int durationX, int durationY,
			Direction direction) {
		this.startLocation = startPosition;
		this.endLocation = endPosition;
		this.durationX = durationX;
		this.durationY = durationY;
		this.direction = direction;
	}

	/**
	 * @return the startLocation
	 */
	public Position getStartLocation() {
		return startLocation;
	}

	/**
	 * @return the endLocation
	 */
	public Position getEndLocation() {
		return endLocation;
	}

	/**
	 * @return the durationX
	 */
	public int getDurationX() {
		return durationX;
	}

	/**
	 * @return the durationY
	 */
	public int getDurationY() {
		return durationY;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

}
