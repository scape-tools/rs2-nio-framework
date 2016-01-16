package main.astraeus.core.net.protocol;

import java.io.IOException;

import main.astraeus.core.net.channel.PlayerChannel;

public abstract class ProtocolStateDecoder {

	/**
	 * Handles the translation of the client's protocol in a periodical and state
	 * based fashion.
	 * 
	 * @param context The context of the channel that is performing the translation.
	 * 
	 * @throws IOException The exception thrown if an error occurs while performing
	 * an input or output operation.
	 */
	public abstract void decode(PlayerChannel context) throws IOException;
}