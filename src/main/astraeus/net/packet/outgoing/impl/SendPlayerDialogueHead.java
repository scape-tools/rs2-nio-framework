package main.astraeus.net.packet.outgoing.impl;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.PacketWriter;
import main.astraeus.net.packet.outgoing.OutgoingPacket;
import main.astraeus.net.protocol.codec.ByteModification;
import main.astraeus.net.protocol.codec.ByteOrder;

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
	public PacketWriter encode(Player player) {
		player.getContext().prepare(this, writer);
		writer.writeShort(interfaceId, ByteModification.ADDITION, ByteOrder.LITTLE);
		return writer;
	}

}
