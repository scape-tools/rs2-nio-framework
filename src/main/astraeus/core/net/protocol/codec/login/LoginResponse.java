package main.astraeus.core.net.protocol.codec.login;

public enum LoginResponse {

	/*
	 * Denotes that the server encountered an unexpected error.
	 */
	UNEXPECTED_SERVER_RESPONSE(1),

	/*
	 * Denotes a successful login attempt.
	 */
	SUCCESSFUL_LOGIN(2),

	/*
	 * Denotes an invalid account name or password.
	 */
	INVALID_CREDENTIALS(3),

	/*
	 * Denotes that the account has been ban.
	 */
	ACCOUNT_IS_BANNED(4),

	/*
	 * Denotes that an account under the same name is already connected.
	 */
	ACCOUNT_IS_ALREADY_LOGGED_IN(5),

	/*
	 * Denotes that the virtual world has been updated.
	 */
	SERVER_UPDATED(6),

	/*
	 * Denotes that the maximum amount of players has been reached.
	 */
	WORLD_IS_FULL(7);

	/**
	 * The value the response holds.
	 */
	private final int value;

	/**
	 * The overloaded class constructor used for instantiation of this
	 * class file.
	 * 
	 * @param value The value the response holds.
	 */
	private LoginResponse(int value) {
		this.value = value;
	}

	/**
	 * Returns the value that the response holds.
	 * 
	 * @return The returned value.
	 */
	public final int getValue() {
		return value;
	}
}