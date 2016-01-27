package main.astraeus.core.net.packet.outgoing.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.PacketWriter;
import main.astraeus.core.net.packet.outgoing.OutgoingPacket;
import main.astraeus.core.net.protocol.codec.ByteModification;

/**
 * The {@link OutgoingPacket} that displays over the side-bar area.
 * 
 * @author SeVen
 */
public class SendInventoryInterface extends OutgoingPacket {
	
	/**
	 * The interface to open.
	 */
	private final int open;
	
	/**
	 * The interface to send on the inventory area.
	 */
	private final int overlay;

	/**
	 * Creates a new {@link SendInventoryInterface}.
	 * 
	 * @param open
	 * 		The interface to open.
	 * 
	 * @param overlay
	 * 		The interface to send on the inventory area.
	 */
	public SendInventoryInterface(int open, int overlay) {
		super(248, 5);
		this.open = open;
		this.overlay = overlay;
	}

	@Override
	public PacketWriter encode(Player player) {
		player.getContext().prepare(this, writer);
		writer.writeShort(open, ByteModification.ADDITION);
		writer.writeShort(overlay);
		return writer;
	}

}
