package main.astraeus.core.net.packet.outgoing.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.packet.PacketHeader;
import main.astraeus.core.net.packet.outgoing.OutgoingPacket;
import main.astraeus.core.net.protocol.codec.ByteValue;

/**
 * The {@link OutgoingPacket} that sends a line of text on an interface.
 * 
 * @author SeVen
 */
public class SendString extends OutgoingPacket {

	/**
	 * The string that will be displayed on the interface.
	 */
	private final String string;
	
	/**
	 * The id of the interface that the text will be displayed on.
	 */
	private final int widget;
	
	/**
	 * Creates a new {@link SendString}.
	 * 
	 * @param string
	 * 		The string to send.
	 * 
	 * @param widget
	 * 		The interface to display on.
	 */
	public SendString(String string, int widget) {
		super(126, PacketHeader.VARIABLE_SHORT, string.length() + 6);
		this.string = string;
		this.widget = widget;
	}

	@Override
	public PacketBuilder encode(Player player) {
		player.getContext().prepare(this, builder);
		builder.putString(string);
		builder.putShort(widget, ByteValue.ADDITION);
		builder.endVariableShortPacketHeader();	
		return builder;
	}

}
