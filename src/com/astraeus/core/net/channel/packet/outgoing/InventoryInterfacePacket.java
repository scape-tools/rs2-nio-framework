package com.astraeus.core.net.channel.packet.outgoing;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.OutgoingPacket;
import com.astraeus.core.net.channel.packet.PacketBuilder;
import com.astraeus.core.net.channel.packet.PacketHeader;
import com.astraeus.core.net.channel.protocol.codec.game.ByteValue;

/**
 * The {@link OutgoingPacket} that displays over the side-bar area.
 * 
 * @author SeVen
 */
public class InventoryInterfacePacket extends OutgoingPacket {
	
	/**
	 * The interface to open.
	 */
	private final int open;
	
	/**
	 * The interface to send on the inventory area.
	 */
	private final int overlay;

	/**
	 * Creates a new {@link InventoryInterfacePacket}.
	 * 
	 * @param open
	 * 		The interface to open.
	 * 
	 * @param overlay
	 * 		The interface to send on the inventory area.
	 */
	public InventoryInterfacePacket(int open, int overlay) {
		super(248, PacketHeader.STANDARD, 5);
		this.open = open;
		this.overlay = overlay;
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		builder.putShort(open, ByteValue.ADDITION);
		builder.putShort(overlay);
		return builder;
	}

}
