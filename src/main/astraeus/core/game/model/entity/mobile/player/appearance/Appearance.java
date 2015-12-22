package main.astraeus.core.game.model.entity.mobile.player.appearance;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.PlayerConstants;

public final class Appearance {

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * The appearance indices.
	 */
	private final int[] appearance = new int[13];

	/**
	 * The clothes color indicates.
	 */
	private final int[] colors = new int[5];

	/**
	 * The character's gender.
	 */
	private Gender gender;

	/**
	 * The overloaded class constructor used for the instantiation of
	 * this class file.
	 * 
	 * @param player The player.
	 */
	public Appearance(Player player) {

		this.player = player;

		defaults();
	}

	/**
	 * Returns an instance of the player.
	 * 
	 * @return The returned instance.
	 */
	public Player getPlayer() {

		return player;
	}

	/**
	 * Returns the collection of appearance indices.
	 * 
	 * @return The returned collection.
	 */
	public int[] getAppearanceIndices() {

		return appearance;
	}

	/**
	 * Returns the collection of color indices.
	 * 
	 * @return The returned collection.
	 */
	public int[] getColorIndices() {

		return colors;
	}

	/**
	 * Returns the gender of the character.
	 * 
	 * @return The returned gender.
	 */
	public Gender getGender() {

		return gender;
	}

	/**
	 * Modifies the gender of the character.
	 * 
	 * @param gender The new modification.
	 */
	public void setGender(Gender gender) {

		this.gender = gender;
	}

	/**
	 * Assigns the default appearance and color values.
	 */
	private void defaults() {

		/*
		 * The default appearance indices.
		 */
		for (int i = 0; i < PlayerConstants.DEFAULT_APPEARANCE.length; i++) {

			appearance[i] = PlayerConstants.DEFAULT_APPEARANCE[i];
		}

		/*
		 * The default clothes color indices.
		 */
		for (int i = 0; i < PlayerConstants.DEFAULT_COLORS.length; i++) {

			colors[i] = PlayerConstants.DEFAULT_COLORS[i];
		}

		/*
		 * The default gender.
		 */
		setGender(Gender.MALE_GENDER);
	}
}