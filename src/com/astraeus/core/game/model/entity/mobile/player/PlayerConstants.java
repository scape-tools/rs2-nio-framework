package com.astraeus.core.game.model.entity.mobile.player;

import com.astraeus.core.Configuration;
import com.astraeus.core.game.model.entity.Position;

public final class PlayerConstants {

	/**
	 * The coordinate point on which the player will spawn upon account
	 * creation.
	 */
	public static final Position START_COORDINATES = new Position(3087, 3502, 0);

	/**
	 * The coordinate point on which the player will spawn after the event
	 * of death.
	 */
	public static final Position RESPAWN_COORDINATES = new Position(3222, 3218, 0);

	/**
	 * The initial message displayed in the chat-box.
	 */
	public static final String WELCOME_MESSAGE = "Welcome to " + Configuration.SERVER_NAME + ".";

	/**
	 * The default appearance indices.
	 */
	public static final int[] DEFAULT_APPEARANCE = {18, 26, 36, 0, 33, 42, 10};

	/**
	 * The default clothes color indices.
	 */
	public static final int[] DEFAULT_COLORS = {7, 8, 9, 5, 0};
}