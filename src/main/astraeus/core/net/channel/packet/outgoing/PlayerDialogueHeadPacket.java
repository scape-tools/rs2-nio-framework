package main.astraeus.core.net.channel.packet.outgoing;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.channel.packet.OutgoingPacket;
import main.astraeus.core.net.channel.packet.PacketBuilder;
import main.astraeus.core.net.channel.protocol.codec.game.ByteOrder;
import main.astraeus.core.net.channel.protocol.codec.game.ByteValue;

/**
 * The {@link OutgoingPacket} that displays a players head model on an interface.
 * 
 * @author SeVen
 */
public class PlayerDialogueHeadPacket extends OutgoingPacket {

	/**
	 * The id of the interface to display the head model on.
	 */
	private final int interfaceId;
	
	/**
	 * Creates a new {@link PlayerDialogueHeadPacket}.
	 * 
	 * @param interfaceId
	 * 		The id of the interface to display the head model on.
	 */
	public PlayerDialogueHeadPacket(int interfaceId) {
		super(185, 10);
		this.interfaceId = interfaceId;
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		builder.putShort(interfaceId, ByteValue.ADDITION, ByteOrder.LITTLE);
		return builder;
	}

}
