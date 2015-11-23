package com.astraeus.core.game.model.entity;

public enum UpdateFlags {

	/**
	 * Updates the player's surrounding map region.
	 */
	UPDATE_MAP_REGION,

	/**
	 * Updates the player's current appearance.
	 */
	UPDATE_APPEARANCE,
	
	/**
	 * Updates a player's facing direction.
	 */
	FACE_POSITION,
	
	/**
	 * Denotes an npc is added to a players local list.
	 */
	REGISTERED_LOCALLY;
}