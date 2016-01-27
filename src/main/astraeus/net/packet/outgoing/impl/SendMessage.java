package main.astraeus.net.packet.outgoing.impl;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.PacketHeader;
import main.astraeus.net.packet.PacketWriter;
import main.astraeus.net.packet.outgoing.OutgoingPacket;

/**
 * The {@link OutgoingPacket} that displays a message on a players chat-box.
 * 
 * @author SeVen
 */
public class SendMessage extends OutgoingPacket {

	/**
	 * The message to be displayed on a players chat-box.
	 */
	private final String message;
	
	/**
	 * Creates a new {@link SendMessage}.
	 * 
	 * @param message
	 * 		The message to display.
	 */
	public SendMessage(String message) {
		super(253, PacketHeader.VARIABLE_BYTE, message.length() + 3);
		this.message = message;
	}

	@Override
	public PacketWriter encode(Player player) {
		player.getContext().prepare(this, writer);
		writer.getBuffer().put(message.getBytes());
		writer.write(10);
		return writer;
	}

}
