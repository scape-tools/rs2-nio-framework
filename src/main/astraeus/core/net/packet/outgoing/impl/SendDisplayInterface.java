package main.astraeus.core.net.packet.outgoing.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.packet.outgoing.OutgoingPacket;

public class SendDisplayInterface extends OutgoingPacket {

	private final int interfaceId;
	
	public SendDisplayInterface(int interfaceId) {
		super(97, 3);
		this.interfaceId = interfaceId;		
	}

	@Override
	public PacketBuilder encode(Player player) {
		player.getContext().prepare(this, builder);
		builder.putShort(interfaceId);
		return builder;
	}

}
