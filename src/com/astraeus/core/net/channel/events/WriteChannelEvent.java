package com.astraeus.core.net.channel.events;

import java.io.IOException;

import com.astraeus.core.net.channel.ChannelEvent;
import com.astraeus.core.net.channel.PlayerIO;
import com.astraeus.core.net.channel.message.PacketBuilder;
import com.astraeus.core.net.channel.message.Packet.PacketHeader;

public final class WriteChannelEvent extends ChannelEvent {

	/**
	 * The message's heading definition.
	 */
	private final PacketHeader definition;

	/**
	 * The internal buffer.
	 */
	private final PacketBuilder buffer;

	/**
	 * The overloaded class constructor used for the instantiation of
	 * this class file.
	 * 
	 * @param definition The message's heading definition.
	 * 
	 * @param buffer The internal buffer.
	 */
	public WriteChannelEvent(PacketHeader definition, PacketBuilder buffer) {
		this.definition = definition;
		this.buffer = buffer;
	}

	@Override
	public void execute(PlayerIO context) throws IOException {
		if (definition.equals(PacketHeader.VARIABLE_BYTE)) {
			buffer.getInternal().put(buffer.getLength(), (byte) (buffer.getInternal().position() - buffer.getLength() - 1));
		} else if (definition.equals(PacketHeader.VARIABLE_SHORT)) {
			buffer.getInternal().putShort(buffer.getLength(), (short) (buffer.getInternal().position() - buffer.getLength() - 2));
		}
		/*
		 * The limit is set to the current position and then the position is set to zero.
		 */
		buffer.getInternal().flip();

		/*
		 * Writes a sequence of bytes to the specified channel.
		 */
		context.getChannel().write(buffer.getInternal());

		/*
		 * Clears this buffer. The position is set to zero, the limit is set to the capacity.
		 */
		buffer.getInternal().clear();
	}
}