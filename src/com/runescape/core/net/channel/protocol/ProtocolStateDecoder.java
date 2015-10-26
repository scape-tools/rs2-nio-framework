package com.runescape.core.net.channel.protocol;

import java.io.IOException;

import com.runescape.core.net.channel.PlayerIO;

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

	/**
	 * Prints an error that occurred while translating the client's protocol.
	 * 
	 * @param context The context of the channel that encountered the error.
	 * 
	 * @param description A description of the error that occurred.
	 * 
	 * @throws IOException The exception thrown if an error occurs while performing
	 * an input or output operation.
	 */
	public abstract void error(PlayerIO context, String description) throws IOException;
}