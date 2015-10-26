package com.runescape.core.game.utility.cryption;

public final class CryptionAlgorithmPair {

	/**
	 * The cryptography algorithm for opcode encoding.
	 */
	private final CryptionAlgorithm encoder;

	/**
	 * The cryptography algorithm for opcode decoding.
	 */
	private final CryptionAlgorithm decoder;

	/**
	 * The overloaded class constructor used for the instantiation of
	 * this class file.
	 * 
	 * @param encoder The cryptography algorithm for opcode encoding.
	 * 
	 * @param decoder The cryptography algorithm for opcode decoding.
	 */
	public CryptionAlgorithmPair(CryptionAlgorithm encoder, CryptionAlgorithm decoder) {

		this.encoder = encoder;

		this.decoder = decoder;
	}

	/**
	 * Returns an instance of the cryptography algorithm for opcode encoding.
	 * 
	 * @return The returned instance.
	 */
	public final CryptionAlgorithm getEncoder() {

		return encoder;
	}

	/**
	 * Returns an instance of the cryptography algorithm for opcode decoding.
	 * 
	 * @return The returned instance.
	 */
	public final CryptionAlgorithm getDecoder() {

		return decoder;
	}
}