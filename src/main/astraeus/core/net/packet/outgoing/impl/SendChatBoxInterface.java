package main.astraeus.core.net.packet.outgoing.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.packet.outgoing.OutgoingPacket;
import main.astraeus.core.net.protocol.codec.ByteOrder;

/**
 * The {@link OutgoingPacket} that shows an interface in the chat-box.
 * 
 * @author SeVen
 */
public class SendChatBoxInterface extends OutgoingPacket {
	
	/**
	 * The id of the interface to display in the chat-box.
	 */
	private final int interfaceId;

	/**
	 * Creates a new {@link SendChatBoxInterface}.
	 * 
	 * @param interfaceId
	 * 		The id of the interface to show in the chat-box.
	 */
	public SendChatBoxInterface(int interfaceId) {
		super(164, 3);
		this.interfaceId = interfaceId;
	}

	@Override
	public PacketBuilder encode(Player player) {
		player.getContext().prepare(this, builder);
		builder.putShort(interfaceId, ByteOrder.LITTLE);
		return builder;
	}

}
