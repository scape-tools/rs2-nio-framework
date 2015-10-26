package com.runescape.core.net.channel.protocol.codec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import com.runescape.core.game.utility.cryption.CryptionAlgorithm;
import com.runescape.core.game.utility.cryption.CryptionAlgorithmPair;
import com.runescape.core.net.ByteValue;
import com.runescape.core.net.channel.PlayerIO;
import com.runescape.core.net.channel.events.WriteChannelEvent;
import com.runescape.core.net.channel.message.PacketBuilder;
import com.runescape.core.net.channel.message.Packet.PacketHeader;
import com.runescape.core.net.channel.protocol.ProtocolConstants;
import com.runescape.core.net.channel.protocol.ProtocolStateDecoder;

public final class LoginPayloadDecoder extends ProtocolStateDecoder {

	@Override
	public void decode(PlayerIO context) throws IOException {

		if (!(context.getBuffer().remaining() < 2)) {

			/*
			 * Denotes the connection status.
			 */
			final int opcode = context.getBuffer().get() & 0xFF;

			/*
			 * The size of the login block.
			 */
			final int loginBlockSize = context.getBuffer().get() & 0xFF;

			/*
			 * The size of the login block after basic encryption.
			 */
			final int encryptedLoginBlockSize = (loginBlockSize - ProtocolConstants.LOGIN_BLOCK_ENCRYPTION_KEY);

			if (opcode != ProtocolConstants.NEW_CONNECTION_OPCODE && opcode != ProtocolConstants.RECONNECTION_OPCODE) {
				error(context, "Invalid connection opcode.");
				return;
			}

			if (encryptedLoginBlockSize < 1) {
				error(context, "Invalid Login-Block size.");
				return;
			}

			if (context.getBuffer().remaining() < loginBlockSize) {
				error(context, "Insufficent buffered memory.");
				return;
			}

			if ((context.getBuffer().get() & 0xFF) != ProtocolConstants.MAGIC_NUMBER_OPCODE) {
				error(context, "Invalid magic number.");
				return;
			}

			if (context.getBuffer().getShort() != ProtocolConstants.PROTOCOL_REVISION) {
				error(context, "Invalid server release.");
				return;
			}

			context.getBuffer().get();

			for (int accumulator = 0; accumulator < ProtocolConstants.RSA_KEY_SKIPPING_AMOUNT; accumulator ++) {
				context.getBuffer().getInt();
			}
			context.getBuffer().get();

			if ((context.getBuffer().get() & 0xFF) == 10) {
				
				/*
				 * The seed generated on the client's end.
				 */
				final long clientSeed = context.getBuffer().getLong();

				/*
				 * The seed generated on the server's end.
				 */
				final long serverSeed = context.getBuffer().getLong();

				/*
				 * The player's identification key.
				 */
				context.getBuffer().getInt();

				/*
				 * The cryptography seeds.
				 */
				final int[] seeds = { (int) (clientSeed >> 32), (int) clientSeed, (int) (serverSeed >> 32), (int) serverSeed };

				/*
				 * The cryptography algorithm for opcode encoding.
				 */
				final CryptionAlgorithm encoder = new CryptionAlgorithm(seeds);

				for (int i = 0; i < seeds.length; i++) {
					seeds[i] += 50;
				}

				/*
				 * The cryptography algorithm for opcode decoding.
				 */
				final CryptionAlgorithm decoder = new CryptionAlgorithm(seeds);

				context.getPlayer().setCryptographyPair(new CryptionAlgorithmPair(encoder, decoder));

				/*
				 * The name of the player's account.
				 */
				final String username = readString(context.getBuffer()).trim();

				/*
				 * The password of the player's account.
				 */
				final String password = readString(context.getBuffer()).trim();

				/*
				 * The local address of the player's computer.
				 */
				final String address = context.getChannel().toString();
				
				context.getPlayer().getDetails().setUsername(username);
				context.getPlayer().getDetails().setPassword(password);
				context.getPlayer().getDetails().setAddress(address);
				final PacketBuilder response = new PacketBuilder();
				
				response.allocate(3);
				
				response.putByte(LoginResponse.SUCCESSFUL_LOGIN.getValue(), ByteValue.STANDARD);
				response.putByte(0, ByteValue.STANDARD);
				response.putByte(0, ByteValue.STANDARD);
				context.execute(new WriteChannelEvent(PacketHeader.EMPTY, response));
				context.getPlayer().getEventListener().add(context.getPlayer());
				context.setProtocolDecoder(new PacketPayloadDecoder());
			} else {
				error(context, "Invalid RSA identifier.");
			}
		} else {
			context.getBuffer().compact();
		}
	}

	@Override
	public void error(PlayerIO context, String description) throws IOException {

		Logger.getLogger(LoginPayloadDecoder.class.getCanonicalName()).info(description);
	}

	/**
	 * Reads a series of bytes in the form of characters and translates that
	 * sequence into a String.
	 * 
	 * @param buffer The internal buffer.
	 * 
	 * @return The result of the operation.
	 */
	public final String readString(ByteBuffer buffer) {
		final StringBuilder builder = new StringBuilder();

		for (char character = '\0'; buffer.hasRemaining() && character != '\n'; character = (char) (buffer.get() & 0xFF)) {
			/*
			 * Appends the String representation of the character argument to this sequence. 
			 */
			builder.append(character);
		}
		return builder.toString();
	}
}