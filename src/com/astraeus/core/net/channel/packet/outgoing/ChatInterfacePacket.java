package com.astraeus.core.net.channel.packet.outgoing;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.OutgoingPacket;
import com.astraeus.core.net.channel.packet.PacketBuilder;
import com.astraeus.core.net.channel.protocol.codec.game.ByteOrder;

/**
 * The {@link OutgoingPacket} that shows an interface in the chat-box.
 * 
 * @author SeVen
 */
public class ChatInterfacePacket extends OutgoingPacket {
	
	/**
	 * The id of the interface to display in the chat-box.
	 */
	private final int interfaceId;

	/**
	 * Creates a new {@link ChatInterfacePacket}.
	 * 
	 * @param interfaceId
	 * 		The id of the interface to show in the chat-box.
	 */
	public ChatInterfacePacket(int interfaceId) {
		super(164, 3);
		this.interfaceId = interfaceId;
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		builder.putShort(interfaceId, ByteOrder.LITTLE);
		return builder;
	}

}
