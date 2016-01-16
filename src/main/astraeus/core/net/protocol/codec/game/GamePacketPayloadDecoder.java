package main.astraeus.core.net.protocol.codec.game;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.astraeus.core.Configuration;
import main.astraeus.core.net.NetworkConstants;
import main.astraeus.core.net.channel.PlayerChannel;
import main.astraeus.core.net.packet.incoming.IncomingPacket;
import main.astraeus.core.net.packet.incoming.IncomingPacketRegistration;
import main.astraeus.core.net.protocol.ProtocolConstants;
import main.astraeus.core.net.protocol.ProtocolStateDecoder;

/**
 * The decoder that decodes incoming {@Packet}s.
 * 
 * @author SeVen
 */
public final class GamePacketPayloadDecoder extends ProtocolStateDecoder {

	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(GamePacketPayloadDecoder.class.getName());

	/**
	 * The default opcode.
	 */
	private int opcode = -1;

	/**
	 * The default length.
	 * 
	 * 0 or more Fixed byte length
	 * -1 Variable byte length
	 * -2 Variable short length
	 */
	private int length = ProtocolConstants.VARIABLE_BYTE;

	/**
	 * The state that this game packet decoder is in.
	 */
	private GamePacketDecoderState state = GamePacketDecoderState.OPCODE;

	@Override
	public void decode(PlayerChannel context) throws IOException {
		while (context.getBuffer().hasRemaining()) {
			switch (state) {
			case OPCODE:
				opcode(context);
				break;

			case SIZE:
				size(context);
				break;

			case PAYLOAD:
				payload(context);
				break;
			}
		}
	}

	/**
	 * The state that determines this packets opcode.
	 * 
	 * @param context
	 *            The channel that this packet is coming from.
	 */
	private void opcode(PlayerChannel context) {
		if (getOpcode() == -1) {
			setOpcode(context.getBuffer().get() - context.getPlayer().getIsaacRandomPair().getEncoder().getNextValue()
					& 0xFF);
			setLength(NetworkConstants.PACKET_SIZES[getOpcode()]);
		}
		state = GamePacketDecoderState.SIZE;
	}

	/**
	 * The state that determines the type of packet.
	 * 
	 * @param context
	 *            The channel that this packet is coming from.
	 */
	private void size(PlayerChannel context) {
		if (getLength() == -1) {
			if (context.getBuffer().remaining() < 1) {
				context.getBuffer().clear();
				System.out.println("Buffer remaining is less than 1: 1");
				return;
			}
			setLength(context.getBuffer().get() & 0xFF);
		}
		if (context.getBuffer().remaining() < getLength()) {
			context.getBuffer().compact();
			System.out.println("Buffer Remaining is less than 1: 2");
			return;
		}
		state = GamePacketDecoderState.PAYLOAD;
	}

	/**
	 * The state that decodes the payload of this packet.
	 * 
	 * @param context
	 *            The session this packet is coming from.
	 */
	private void payload(PlayerChannel context) {
		try {
			byte[] payloadLength = new byte[getLength()];
			context.getBuffer().get(payloadLength);

			ByteBuffer packetPayload = ByteBuffer.allocate(getLength());

			packetPayload.put(payloadLength);
			packetPayload.flip();

			IncomingPacket packet = new IncomingPacket(packetPayload, getOpcode(), getLength());

			IncomingPacketRegistration.dispatchToListener(packet, context.getPlayer());

			if (Configuration.SERVER_DEBUG && context.getPlayer().isServerDebug() && packet.getOpcode() != 0)
				logger.log(Level.INFO,
						String.format("[Packet] - Opcode: %d Length: %d", packet.getOpcode(), packet.getLength()));

		} finally {
			state = GamePacketDecoderState.OPCODE;
			setOpcode(-1);
			setLength(-1);
		}
	}

	/**
	 * Gets the length this packet.
	 * 
	 * @return The returned length.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Sets the length of this packet.
	 * 
	 * @param length
	 *		The new length of this packet.
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Gets the opcode of a packet.
	 * 
	 * @return The opcode.
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * Sets a new opcode value.
	 * 
	 * @param opcode
	 *		The opcode to be set.
	 */
	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

}