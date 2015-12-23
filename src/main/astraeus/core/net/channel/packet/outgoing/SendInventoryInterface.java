package main.astraeus.core.net.channel.packet.outgoing;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.channel.packet.OutgoingPacket;
import main.astraeus.core.net.channel.packet.PacketBuilder;
import main.astraeus.core.net.channel.protocol.codec.game.ByteValue;

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
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		builder.putShort(open, ByteValue.ADDITION);
		builder.putShort(overlay);
		return builder;
	}

}
