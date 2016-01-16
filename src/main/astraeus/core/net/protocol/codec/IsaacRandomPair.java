package main.astraeus.core.net.protocol.codec;

/**
 * A pair of two {@link IsaacRandom} random number generators used as a stream cipher. One takes the role of an encoder
 * for this endpoint, the other takes the role of a decoder for this endpoint.
 *
 * @author Graham
 */
public final class IsaacRandomPair {

	/**
	 * The cryptography algorithm for opcode encoding.
	 */
	private final IsaacRandom encoder;

	/**
	 * The cryptography algorithm for opcode decoding.
	 */
	private final IsaacRandom decoder;

	/**
	 * The overloaded class constructor used for the instantiation of
	 * this class file.
	 * 
	 * @param encoder The cryptography algorithm for opcode encoding.
	 * 
	 * @param decoder The cryptography algorithm for opcode decoding.
	 */
	public IsaacRandomPair(IsaacRandom encoder, IsaacRandom decoder) {
		this.encoder = encoder;
		this.decoder = decoder;
	}

	/**
	 * Returns an instance of the cryptography algorithm for opcode encoding.
	 * 
	 * @return The returned instance.
	 */
	public final IsaacRandom getEncoder() {
		return encoder;
	}

	/**
	 * Returns an instance of the cryptography algorithm for opcode decoding.
	 * 
	 * @return The returned instance.
	 */
	public final IsaacRandom getDecoder() {
		return decoder;
	}
}