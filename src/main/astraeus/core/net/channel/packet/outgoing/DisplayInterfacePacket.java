package main.astraeus.core.net.channel.packet.outgoing;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.channel.packet.OutgoingPacket;
import main.astraeus.core.net.channel.packet.PacketBuilder;

public class DisplayInterfacePacket extends OutgoingPacket {

	private final int interfaceId;
	
	public DisplayInterfacePacket(int interfaceId) {
		super(97, 3);
		this.interfaceId = interfaceId;		
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		builder.putShort(interfaceId);
		return builder;
	}

}
