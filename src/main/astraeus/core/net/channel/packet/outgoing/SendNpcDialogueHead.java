package main.astraeus.core.net.channel.packet.outgoing;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.channel.packet.OutgoingPacket;
import main.astraeus.core.net.channel.packet.PacketBuilder;
import main.astraeus.core.net.channel.packet.PacketHeader;
import main.astraeus.core.net.channel.protocol.codec.game.ByteOrder;
import main.astraeus.core.net.channel.protocol.codec.game.ByteValue;

/**
 * The {@link OutgoingPacket} that displays an npcs head model on an interface.
 * 
 * @author SeVen
 */
public class SendNpcDialogueHead extends OutgoingPacket {
	
	/**
	 * The id of the npc to display.
	 */
	private final int npcId;
	
	/**
	 * The id of the interface to display the head model on.
	 */
	private final int interfaceId;

	/**
	 * Creates a new {@link SendNpcDialogueHead}.
	 * 
	 * @param npcId
	 * 		The id of the npc to display.
	 * 
	 * @param interfaceId
	 * 		The id of the interface to show the head model on.
	 */
	public SendNpcDialogueHead(int npcId, int interfaceId) {
		super(75, PacketHeader.STANDARD, 15);
		this.npcId = npcId;
		this.interfaceId = interfaceId;
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		builder.putShort(npcId, ByteValue.ADDITION, ByteOrder.LITTLE);
		builder.putShort(interfaceId, ByteValue.ADDITION, ByteOrder.LITTLE);
		return builder;
	}

}
