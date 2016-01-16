package main.astraeus.core.net.packet.outgoing;

import java.io.IOException;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.packet.PacketHeader;

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
	 * Encodes a packet for it to be sent through a channel.
	 * 
	 * @throws IOException
	 * 		The exception thrown if an error occurs while building or dispensing the packet.
	 */
	public abstract PacketBuilder encode(Player player);
	
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
		this.builder = new PacketBuilder(opcode, header).allocate(allocate);
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
