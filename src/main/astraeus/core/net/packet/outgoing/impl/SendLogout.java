package main.astraeus.core.net.packet.outgoing.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.packet.PacketHeader;
import main.astraeus.core.net.packet.outgoing.OutgoingPacket;

/**
 * The {@link OutgoingPacket} that logs a player out of the game.
 * 
 * @author SeVen
 */
public class SendLogout extends OutgoingPacket {

	/**
	 * Creates a new {@link SendLogout}.
	 */
	public SendLogout() {
		super(109, PacketHeader.STANDARD,  1);
	}

	@Override
	public PacketBuilder encode(Player player) {
		player.getContext().prepare(this, builder);
		return builder;
	}

}
