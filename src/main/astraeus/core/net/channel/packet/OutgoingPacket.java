package main.astraeus.core.net.channel.packet;

import java.io.IOException;
import java.nio.ByteBuffer;

import main.astraeus.core.game.model.entity.mobile.player.Player;

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
	 * The builder that will be used to build a buffer.
	 */
	protected PacketBuilder builder;
	
	/**
	 * Sends the packet to a channel.
	 * 
	 * @throws IOException
	 * 		The exception thrown if an error occurs while building or dispensing the packet.
	 */
	public abstract PacketBuilder dispatch(Player player);
	
	/**
	 * Creates a new {@link OutgoingPacket} with a default
	 * {@link PacketHeader} of {@code STANDARD}.
	 * 
	 * @param opcode
	 * 		The id of this packet.
	 * 
	 * @param allocate
	 * 		The allocation of this buffer.
	 */
	public OutgoingPacket(int opcode, int allocate) {
		this(opcode, PacketHeader.STANDARD, allocate);
	}
	
	/**
	 * Creates a new {@link OutgoingPacket}.
	 * 
	 * @param opcode
	 * 		The id of this packet.
	 * 
	 * @param header
	 * 		The header for this packet.
	 * 
	 * @param allocate
	 * 		The allocation of this buffer.
	 */
	public OutgoingPacket(int opcode, PacketHeader header, int allocate) {
		this.opcode = opcode;
		this.header = header;
		this.builder = new PacketBuilder(ByteBuffer.allocate(allocate));
	}
	
	/**
	 * @return the opcode
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * @return the header
	 */
	public PacketHeader getHeader() {
		return header;
	}

	/**
	 * @return the builder
	 */
	public PacketBuilder getBuilder() {
		return builder;
	}

}
