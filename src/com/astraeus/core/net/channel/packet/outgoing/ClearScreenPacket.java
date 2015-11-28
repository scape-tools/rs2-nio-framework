package com.astraeus.core.net.channel.packet.outgoing;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.OutgoingPacket;
import com.astraeus.core.net.channel.packet.PacketBuilder;

/**
 * The {@link OutgoingPacket} that clears a players screen of all open interfaces.
 * 
 * @author SeVen
 */
public class ClearScreenPacket extends OutgoingPacket {

	/**
	 * Creates a new {@link ClearScreenPacket}.
	 */
	public ClearScreenPacket() {
		super(219, 1);
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		return builder;
	}

}
