package main.astraeus.core.net.channel.packet.outgoing;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.channel.packet.OutgoingPacket;
import main.astraeus.core.net.channel.packet.PacketBuilder;
import main.astraeus.core.net.channel.packet.PacketHeader;

/**
 * The {@link OutgoingPacket} that logs a player out of the game.
 * 
 * @author SeVen
 */
public class LogoutPacket extends OutgoingPacket {

	/**
	 * Creates a new {@link LogoutPacket}.
	 */
	public LogoutPacket() {
		super(109, PacketHeader.STANDARD,  1);
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		return builder;
	}

}
