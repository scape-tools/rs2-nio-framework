package com.astraeus.core.net.channel.events;

import java.io.IOException;

import com.astraeus.core.net.channel.ChannelEvent;
import com.astraeus.core.net.channel.PlayerIO;
import com.astraeus.core.net.channel.message.PacketBuilder;
import com.astraeus.core.net.channel.message.Packet.PacketHeader;

public final class WriteChannelEvent extends ChannelEvent {

	/**
	 * The header for the packet.
	 */
	private final PacketHeader header;

	/**
	 * The buffer being used.
	 */
	private final PacketBuilder buffer;
	
	/**
	 * Creates a new {@link WriteChannelEvent} with a
	 * default {@code PacketHeader} of {@code EMPTY}.
	 * 
	 * @param buffer
	 * 		The buffer being used.
	 */
	public WriteChannelEvent(PacketBuilder buffer) {
		this(PacketHeader.EMPTY, buffer);
	}

	/**
	 * Creates a new {@link WriteChannelEvent}.
	 * 
	 * @param header
	 * 		The header for this channel event.
	 * 
	 * @param buffer
	 * 		The buffer being used.
	 */
	public WriteChannelEvent(PacketHeader header, PacketBuilder buffer) {
		this.header = header;
		this.buffer = buffer;
	}

	@Override
	public void execute(PlayerIO context) throws IOException {
		if (header.equals(PacketHeader.VARIABLE_BYTE)) {
			buffer.getInternal().put(buffer.getLength(), (byte) (buffer.getInternal().position() - buffer.getLength() - 1));
		} else if (header.equals(PacketHeader.VARIABLE_SHORT)) {
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