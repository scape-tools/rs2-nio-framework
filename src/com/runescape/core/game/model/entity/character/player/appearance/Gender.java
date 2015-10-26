package com.runescape.core.game.model.entity.character.player.appearance;

public enum Gender {

	/*
	 * Denotes a male character.
	 */
	MALE_GENDER(0),

	/*
	 * Denotes a female character.
	 */
	FEMALE_GENDER(1);

	/**
	 * The numerical gender indicator.
	 */
	private final int indicator;

	/**
	 * The overloaded class constructor used for the instantiation of
	 * this class file.
	 * 
	 * @param indicator The numerical gender indicator.
	 */
	private Gender(int indicator) {

		this.indicator = indicator;
	}

	/**
	 * Returns the numerical gender indicator.
	 * 
	 * @return The returned indicator.
	 */
	public int getIndicator() {

		return indicator;
	}
}