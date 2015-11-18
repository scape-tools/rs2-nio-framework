package com.runescape.core.net.channel.protocol.codec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.runescape.core.Configuration;
import com.runescape.core.net.NetworkConstants;
import com.runescape.core.net.channel.PlayerIO;
import com.runescape.core.net.channel.message.IncomingPacketRegistration;
import com.runescape.core.net.channel.message.Packet;
import com.runescape.core.net.channel.protocol.ProtocolStateDecoder;

public final class PacketPayloadDecoder extends ProtocolStateDecoder {
	
	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(PacketPayloadDecoder.class.getName());

	private int length = -1, opcode = -1;
	
	@Override
	public void decode(PlayerIO context) throws IOException {
		
		while (context.getBuffer().hasRemaining()) {
			if(getOpcode() == -1) {
				setOpcode(context.getBuffer().get() - context.getPlayer().getCryptographyPair().getEncoder().getNextValue() & 0xFF);
				setLength(NetworkConstants.PACKET_SIZES[getOpcode()]);
			}
			if (getLength() == -1) {
				if (context.getBuffer().remaining() < 1 ) {
					context.getBuffer().clear();
					System.out.println("Buffer remaining is less than 1: 1");
					return;
				}
				setLength(context.getBuffer().get() & 0xFF);
			}
			if(context.getBuffer().remaining() < getLength()) {
				context.getBuffer().compact();
				System.out.println("Buffer Remaining is less than 1: 2");
				return;
			}
			byte[] payloadLength = new byte[getLength()];
			context.getBuffer().get(payloadLength);
			
			ByteBuffer packetPayload = ByteBuffer.allocate(getLength());

			packetPayload.put(payloadLength);
			packetPayload.flip();
			
			Packet packet = new Packet(packetPayload, getOpcode(), getLength());

			IncomingPacketRegistration.dispatchToListener(packet, context.getPlayer());
			
			if(Configuration.SERVER_DEBUG && packet.getOpcode() != 0)
			logger.log(Level.INFO, String.format("[Packet] - Opcode: %d Length: %d", packet.getOpcode(), packet.getLength()));
	
			setOpcode(-1);
			setLength(-1);
		}
	}
	
	/**
	 * Returns the numerical length of the packet.
	 * 
	 * @return The returned length.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Modifies the numerical length of the packet.
	 * 
	 * @param length The new modification.
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Returns the numerical opcode of the packet.
	 * 
	 * @return The returned opcode.
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * Modifies the numerical opcode of the packet.
	 * 
	 * @param opcode The new modification.
	 */
	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}
	
}