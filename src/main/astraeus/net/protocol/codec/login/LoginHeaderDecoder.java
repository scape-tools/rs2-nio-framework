package main.astraeus.net.protocol.codec.login;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.logging.Logger;

import main.astraeus.net.channel.PlayerChannel;
import main.astraeus.net.channel.events.WriteChannelEvent;
import main.astraeus.net.packet.PacketHeader;
import main.astraeus.net.packet.PacketWriter;
import main.astraeus.net.protocol.ProtocolStateDecoder;

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
		
		final PacketWriter response = new PacketWriter(ByteBuffer.allocate(17));		
		response.write(0);
		response.writeLong(0);		
		response.writeLong(random.nextLong());
		context.execute(new WriteChannelEvent(PacketHeader.EMPTY, response));
		context.setProtocolDecoder(new LoginPayloadDecoder());
	}

}