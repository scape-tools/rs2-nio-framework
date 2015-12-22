package main.astraeus.core.net.channel.packet.outgoing;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.channel.packet.OutgoingPacket;
import main.astraeus.core.net.channel.packet.PacketBuilder;
import main.astraeus.core.net.channel.packet.PacketHeader;
import main.astraeus.core.net.channel.protocol.codec.game.ByteValue;

/**
 * The {@link OutgoingPacket} that sends a line of text on an interface.
 * 
 * @author SeVen
 */
public class SendStringPacket extends OutgoingPacket {

	/**
	 * The string that will be displayed on the interface.
	 */
	private final String string;
	
	/**
	 * The id of the interface that the text will be displayed on.
	 */
	private final int widget;
	
	/**
	 * Creates a new {@link SendStringPacket}.
	 * 
	 * @param string
	 * 		The string to send.
	 * 
	 * @param widget
	 * 		The interface to display on.
	 */
	public SendStringPacket(String string, int widget) {
		super(126, PacketHeader.VARIABLE_SHORT, string.length() + 6);
		this.string = string;
		this.widget = widget;
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		builder.putString(string);
		builder.putShort(widget, ByteValue.ADDITION);
		builder.endVariableShortPacketHeader();	
		return builder;
	}

}
