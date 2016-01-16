package main.astraeus.core.net.channel.events;

import java.io.IOException;

import main.astraeus.core.net.channel.ChannelEvent;
import main.astraeus.core.net.channel.PlayerChannel;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.packet.PacketHeader;

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
	public void execute(PlayerChannel context) throws IOException {
		if (header.equals(PacketHeader.VARIABLE_BYTE)) {
			buffer.getBuffer().put(buffer.getLength(), (byte) (buffer.getBuffer().position() - buffer.getLength() - 1));
		} else if (header.equals(PacketHeader.VARIABLE_SHORT)) {
			buffer.getBuffer().putShort(buffer.getLength(), (short) (buffer.getBuffer().position() - buffer.getLength() - 2));
		}
		/*
		 * The limit is set to the current position and then the position is set to zero.
		 */
		buffer.getBuffer().flip();

		/*
		 * Writes a sequence of bytes to the specified channel.
		 */
		context.getChannel().write(buffer.getBuffer());

		/*
		 * Clears this buffer. The position is set to zero, the limit is set to the capacity.
		 */
		buffer.getBuffer().clear();
	}
}