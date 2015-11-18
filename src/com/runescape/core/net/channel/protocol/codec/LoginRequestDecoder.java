package com.runescape.core.net.channel.protocol.codec;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.runescape.core.net.channel.PlayerIO;
import com.runescape.core.net.channel.protocol.ProtocolConstants;
import com.runescape.core.net.channel.protocol.ProtocolStateDecoder;

public final class LoginRequestDecoder extends ProtocolStateDecoder {
	
	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(LoginRequestDecoder.class.getName());

	@Override
	public void decode(PlayerIO context) throws IOException {
		/*
		 * Denotes the appropriate server.
		 */
		final int opcode = context.getBuffer().get() & 0xFF;

		if (opcode == ProtocolConstants.GAME_SEVER_OPCODE) {
			/*
			 * A hash of the player's account name, theorized to determine a login
			 * server.
			 */
			final int hash = context.getBuffer().get() & 0xFF;

			context.setProtocolDecoder(new LoginHeaderDecoder());
			Logger.getLogger(LoginRequestDecoder.class.getSimpleName()).info("New Connection : " + context.getChannel() + ". Hash : " + hash + ".");

		} else if (opcode == ProtocolConstants.FILE_SERVER_OPCODE) {
			logger.log(Level.INFO, "File-Server indication opcode encountered.");
		} else {
			logger.log(Level.SEVERE, "Invalid server request opcode: {0}", opcode);
		}
	}

}