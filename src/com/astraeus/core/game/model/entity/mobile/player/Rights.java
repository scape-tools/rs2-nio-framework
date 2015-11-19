package com.astraeus.core.game.model.entity.mobile.player;

/**
 * The rights of a player.
 * 
 * @author SeVen
 */
public enum Rights {	
	
	/**
	 * A normal player.
	 */
	PLAYER(0, 0),
	
	/**
	 * A player moderator.
	 */
	MODERATOR(1, 1),

	/**
	 * An admin of the server.
	 */
	ADMINISTRATOR(2, 2),

	/**
	 * A developer for the server.
	 */
	DEVELOPER(2, 3);
	
	/**
	 * The rights of a player from the client.
	 */
	private final int protocolValue;
	
	/**
	 * The rights  of a player from the server.
	 */
	private final int value;
	
	/**
	 * Create a new {@link Rights}.
	 *
	 * @param protocolValue
	 *		The value of this rank as seen by the client.
	 * @param value
	 *		The value of this rank as seen by the server.
	 */
	private Rights(int protocolValue, int value) {
		this.protocolValue = protocolValue;
		this.value = value;
	}
	
	/**
	 * Checks to see if a specified rights is greater than
	 * the rights in the enumerated type.
	 * 
	 * @param other
	 * 		The other rights to check.
	 * 
	 * @return {@code true} If this right is greater than the enumerated type, {@link false} otherwise.
	 */
	public boolean greater(Rights other) {
		return this.ordinal() > other.ordinal();
	}
	
	/**
	 * Checks to see if a specified rights is greater or equal than
	 * the rights in the enumerated type.
	 * 
	 * @param other
	 * 		The other rights to check.
	 * 
	 * @return {@code true} If this right is greater than the enumerated type, {@link false} otherwise.
	 */
	public boolean greaterOrEqual(Rights other) {
		return this.ordinal() >= other.ordinal();
	}
	
	/**
	 * Checks to see if a specified rights is less than
	 * the rights in the enumerated type.
	 * 
	 * @param other
	 * 		The other rights to check.
	 * 
	 * @return {@code true} If this right is less than the enumerated type, {@link false} otherwise.
	 */
	public boolean less(Rights other) {
		return this.ordinal() < other.ordinal();
	}
	
	/**
	 * Checks to see if a specified rights is less than
	 * the rights in the enumerated type.
	 * 
	 * @param other
	 * 		The other rights to check.
	 * 
	 * @return {@code true} If this right is less than the enumerated type, {@link false} otherwise.
	 */
	public boolean lessOrEqual(Rights other) {
		return this.ordinal() <= other.ordinal();
	}
	
	/**
	 * Checks to see if a specified rights is equal to
	 * the rights in the enumerated type.
	 * 
	 * @param other
	 * 		The other rights to check.
	 * 
	 * @return {@code true} If this right is equal to the enumerated type, {@link false} otherwise.
	 */
	public boolean equal(Rights other) {
		return this.ordinal() <= other.ordinal();
	}

	/**
	 * @return the protocolValue
	 */
	public int getProtocolValue() {
		return protocolValue;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
