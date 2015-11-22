package com.astraeus.core.net.channel.packet;

import java.io.IOException;

import com.astraeus.core.net.channel.PlayerChannel;

/**
 * Represents a single outgoing packet.
 * 
 * @author SeVen
 */
public abstract class OutgoingPacket {
	
	/**
	 * The opcode of this packet.
	 */
	private final int opcode;
	
	/**
	 * The header of this packet.
	 */
	private final PacketHeader header;
	
	/**
	 * The context of the channel that is sending the packet.
	 */
	protected PlayerChannel context;
	
	/**
	 * The builder that will be used to build a buffer.
	 */
	protected PacketBuilder builder;
	
	/**
	 * Sends the packet to a channel.
	 * 
	 * @throws IOException
	 * 		The exception thrown if an error occurs while building or dispensing the packet.
	 */
	public abstract PacketBuilder dispatch() throws IOException;
	
	public OutgoingPacket(int opcode, PacketHeader header, PlayerChannel context, int allocate) {
		this.opcode = opcode;
		this.header = header;
		this.context = context;
		//this.builder = builder;
		//context.prepare(builder);
	}

}
