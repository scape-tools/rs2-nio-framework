package com.runescape.core.net.channel.protocol.codec;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.logging.Logger;

import com.runescape.core.net.ByteValue;
import com.runescape.core.net.channel.PlayerIO;
import com.runescape.core.net.channel.events.WriteChannelEvent;
import com.runescape.core.net.channel.message.PacketBuilder;
import com.runescape.core.net.channel.message.Packet.PacketHeader;
import com.runescape.core.net.channel.protocol.ProtocolStateDecoder;

public final class LoginHeaderDecoder extends ProtocolStateDecoder {
	
	/**
	 * The single logger for this class. 
	 */
	public static final Logger logger = Logger.getLogger(LoginHeaderDecoder.class.getName());

	/**
	 * Generates random numbers via secure cryptography. Generates the
	 * session key for packet encryption.
	 */
	private final SecureRandom random = new SecureRandom();

	@Override
	public void decode(PlayerIO context) throws IOException {
		
		final PacketBuilder response = new PacketBuilder();
		
		response.allocate(17);
		
		response.putByte(0, ByteValue.STANDARD);
		response.putLong(0, ByteValue.STANDARD);		
		response.putLong(random.nextLong(), ByteValue.STANDARD);
		context.execute(new WriteChannelEvent(PacketHeader.EMPTY, response));
		context.setProtocolDecoder(new LoginPayloadDecoder());
	}

	@Override
	public void error(PlayerIO context, String description) throws IOException {
		logger.info(description);
	}
}