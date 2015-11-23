package com.astraeus.core.net.channel.packet.outgoing;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.OutgoingPacket;
import com.astraeus.core.net.channel.packet.PacketBuilder;
import com.astraeus.core.net.channel.packet.PacketHeader;

/**
 * The {@link OutgoingPacket} that displays a message on a players chat-box.
 * 
 * @author SeVen
 */
public class ChatBoxMessagePacket extends OutgoingPacket {

	/**
	 * The message to be displayed on a players chat-box.
	 */
	private final String message;
	
	/**
	 * Creates a new {@link ChatBoxMessagePacket}.
	 * 
	 * @param message
	 * 		The message to display.
	 */
	public ChatBoxMessagePacket(String message) {
		super(253, PacketHeader.VARIABLE_BYTE, message.length() + 3);
		this.message = message;
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		builder.getInternal().put(message.getBytes());
		builder.putByte(10);
		return builder;
	}

}
