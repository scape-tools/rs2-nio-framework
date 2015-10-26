package com.runescape.core.net.channel.events;

import java.io.IOException;

import com.runescape.core.net.ByteValue;
import com.runescape.core.net.channel.PlayerIO;
import com.runescape.core.net.channel.ChannelEvent;
import com.runescape.core.net.channel.message.PacketBuilder;
import com.runescape.core.net.channel.message.Packet.PacketHeader;

public final class PrepareChannelEvent extends ChannelEvent {

	/**
	 * The message's heading definition.
	 */
	private final PacketHeader definition;

	/**
	 * The internal buffer.
	 */
	private final PacketBuilder buffer;

	/**
	 * The numerical opcode.
	 */
	private final int opcode;

	/**
	 * The overloaded class constructor used for the instantiation of this
	 * class file.
	 * 
	 * @param definition The message's heading definition.
	 * 
	 * @param buffer The internal buffer.
	 * 
	 * @param opcode The numerical opcode.
	 */
	public PrepareChannelEvent(PacketHeader definition, PacketBuilder buffer, int opcode) {
		this.definition = definition;
		this.buffer = buffer;
		this.opcode = opcode;
	}

	@Override
	public void execute(PlayerIO context) throws IOException {
		/*
		 * If a packet does not have an empty heading then the opcode must be encrypted
		 * with a cryptographic cipher. After the encryption if the packet's heading is
		 * defined as a byte or a short the length must be indicated by that primitive.
		 */
		if (!definition.equals(PacketHeader.EMPTY)) {
			buffer.putByte(opcode + context.getPlayer().getCryptographyPair().getDecoder().getNextValue(), ByteValue.STANDARD);
			
			if (definition.equals(PacketHeader.VARIABLE_BYTE)) {
				buffer.setLength(buffer.getInternal().position());
				buffer.putByte(0, ByteValue.STANDARD);
			} else if (definition.equals(PacketHeader.VARIABLE_SHORT)) {
				buffer.setLength(buffer.getInternal().position());
				buffer.putShort(0, ByteValue.STANDARD);
			}
		}
	}
}