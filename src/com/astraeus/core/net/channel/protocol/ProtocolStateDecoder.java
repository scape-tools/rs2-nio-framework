package com.astraeus.core.net.channel.protocol;

import java.io.IOException;

import com.astraeus.core.net.channel.PlayerIO;

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
	public abstract void decode(PlayerIO context) throws IOException;
}