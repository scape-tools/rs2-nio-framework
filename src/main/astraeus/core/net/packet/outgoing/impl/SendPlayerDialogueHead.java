package main.astraeus.core.net.packet.outgoing.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.packet.outgoing.OutgoingPacket;
import main.astraeus.core.net.protocol.codec.ByteOrder;
import main.astraeus.core.net.protocol.codec.ByteValue;

/**
 * The {@link OutgoingPacket} that displays a players head model on an interface.
 * 
 * @author SeVen
 */
public class SendPlayerDialogueHead extends OutgoingPacket {

	/**
	 * The id of the interface to display the head model on.
	 */
	private final int interfaceId;
	
	/**
	 * Creates a new {@link SendPlayerDialogueHead}.
	 * 
	 * @param interfaceId
	 * 		The id of the interface to display the head model on.
	 */
	public SendPlayerDialogueHead(int interfaceId) {
		super(185, 10);
		this.interfaceId = interfaceId;
	}

	@Override
	public PacketBuilder encode(Player player) {
		player.getContext().prepare(this, builder);
		builder.putShort(interfaceId, ByteValue.ADDITION, ByteOrder.LITTLE);
		return builder;
	}

}
