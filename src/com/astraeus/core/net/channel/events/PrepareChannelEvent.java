package com.astraeus.core.net.channel.events;

import java.io.IOException;

import com.astraeus.core.net.channel.ChannelEvent;
import com.astraeus.core.net.channel.PlayerChannel;
import com.astraeus.core.net.channel.message.PacketBuilder;
import com.astraeus.core.net.channel.message.Packet.PacketHeader;
import com.astraeus.core.net.channel.protocol.codec.game.ByteValue;

public final class PrepareChannelEvent extends ChannelEvent {

	/**
	 * The header for this message.
	 */
	private final PacketHeader header;

	/**
	 * The buffer for this message.
	 */
	private final PacketBuilder buffer;

	/**
	 * The opcode for this message.
	 */
	private final int opcode;
	
	/**
	 * Creates a new {@link PrepareChannelEvent} with a default
	 * {@code PacketHeader} of {@code EMPTY}.
	 * 
	 * @param header
	 * 		The header for this message.
	 * 
	 * @param buffer
	 * 		The buffer for this message.
	 * 
	 * @param opcode
	 * 		The opcode for this message.
	 */
	public PrepareChannelEvent(PacketBuilder buffer, int opcode) {
		this(PacketHeader.EMPTY, buffer, opcode);
	}

	/**
	 * Creates a new {@link PrepareChannelEvent}.
	 * 
	 * @param header
	 * 		The header for this message.
	 * 
	 * @param buffer
	 * 		The buffer for this message.
	 * 
	 * @param opcode
	 * 		The opcode for this message.
	 */
	public PrepareChannelEvent(PacketHeader header, PacketBuilder buffer, int opcode) {
		this.header = header;
		this.buffer = buffer;
		this.opcode = opcode;
	}

	@Override
	public void execute(PlayerChannel context) throws IOException {
		/*
		 * If a packet does not have an empty heading then the opcode must be encrypted
		 * with a cryptographic cipher. After the encryption if the packet's heading is
		 * defined as a byte or a short the length must be indicated by that primitive.
		 */
		if (!header.equals(PacketHeader.EMPTY)) {
			buffer.putByte(opcode + context.getPlayer().getIsaacRandomPair().getDecoder().getNextValue(), ByteValue.STANDARD);
			
			if (header.equals(PacketHeader.VARIABLE_BYTE)) {
				buffer.setLength(buffer.getInternal().position());
				buffer.putByte(0, ByteValue.STANDARD);
			} else if (header.equals(PacketHeader.VARIABLE_SHORT)) {
				buffer.setLength(buffer.getInternal().position());
				buffer.putShort(0, ByteValue.STANDARD);
			}
		}
	}
}