package com.astraeus.core.game.model.entity.mobile.player;

public final class PlayerDetails {

	/**
	 * The owner of these details.
	 */
	private final Player player;
	
	/**
	 * The known address of the player's computer.
	 */
	private String address;
	
	private String UUID;

	/**
	 * The name of the player's account.
	 */
	private String username;

	/**
	 * The password of the player's account.
	 */
	private String password;
	
	/**
	 * The rights of this player.
	 */
	private Rights rights = Rights.PLAYER;

	/**
	 * The overloaded class constructor used for the instantiation of
	 * this class file.
	 * 
	 * @param player The owner of these details.
	 */
	public PlayerDetails(Player player) {

		this.player = player;
	}

	/**
	 * Returns an instance of the owner of these details.
	 * 
	 * @return The returned instance.
	 */
	public Player getPlayer() {

		return player;
	}

	/**
	 * Returns the player's account name.
	 * 
	 * @return The returned name.
	 */
	public String getUsername() {

		return username;
	}

	/**
	 * Modifies the player's account name.
	 * 
	 * @param username The new modification.
	 */
	public void setUsername(String username) {

		this.username = username;
	}

	/**
	 * Returns the player's account password.
	 * 
	 * @return The returned password.
	 */
	public String getPassword() {

		return password;
	}

	/**
	 * Modifies the player's account password.
	 * 
	 * @param password The new modification.
	 */
	public void setPassword(String password) {

		this.password = password;
	}
	
	/**
	 * @return the rights
	 */
	public Rights getRights() {
		return rights;
	}

	/**
	 * @param rights the rights to set
	 */
	public void setRights(Rights rights) {
		this.rights = rights;
	}

	/**
	 * Returns the known address of the player's computer.
	 * 
	 * @return The returned address.
	 */
	public String getAddress() {

		return address;
	}

	/**
	 * Modifies the known address of the player's computer.
	 * 
	 * @param address The new modification.
	 */
	public void setAddress(String address) {

		this.address = address;
	}

	/**
	 * @return the UUID
	 */
	public String getUUID() {
		return UUID;
	}

	/**
	 * @param UUID the UUID to set
	 */
	public void setUUID(String UUID) {
		this.UUID = UUID;
	}
	
}