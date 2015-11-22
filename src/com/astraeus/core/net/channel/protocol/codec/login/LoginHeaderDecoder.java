package com.astraeus.core.net.channel.protocol.codec.login;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.logging.Logger;

import com.astraeus.core.net.channel.PlayerChannel;
import com.astraeus.core.net.channel.events.WriteChannelEvent;
import com.astraeus.core.net.channel.message.Packet.Header;
import com.astraeus.core.net.channel.message.PacketBuilder;
import com.astraeus.core.net.channel.protocol.ProtocolStateDecoder;
import com.astraeus.core.net.channel.protocol.codec.game.ByteValue;

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
	public void decode(PlayerChannel context) throws IOException {
		
		final PacketBuilder response = new PacketBuilder();
		
		response.allocate(17);
		
		response.putByte(0, ByteValue.STANDARD);
		response.putLong(0, ByteValue.STANDARD);		
		response.putLong(random.nextLong(), ByteValue.STANDARD);
		context.execute(new WriteChannelEvent(Header.EMPTY, response));
		context.setProtocolDecoder(new LoginPayloadDecoder());
	}

}