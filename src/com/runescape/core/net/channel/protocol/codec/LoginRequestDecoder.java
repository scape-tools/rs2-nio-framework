package com.runescape.core.net.channel.protocol.codec;

import java.io.IOException;
import java.util.logging.Logger;

import com.runescape.core.net.channel.PlayerIO;
import com.runescape.core.net.channel.protocol.ProtocolConstants;
import com.runescape.core.net.channel.protocol.ProtocolStateDecoder;

public final class LoginRequestDecoder extends ProtocolStateDecoder {

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

			error(context, "File-Server indication opcode encountered.");
		}
	}

	@Override
	public void error(PlayerIO context, String description) throws IOException {

		Logger.getLogger(LoginRequestDecoder.class.getCanonicalName()).info(description);
	}
}