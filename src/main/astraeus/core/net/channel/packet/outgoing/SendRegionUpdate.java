package main.astraeus.core.net.channel.packet.outgoing;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.channel.packet.OutgoingPacket;
import main.astraeus.core.net.channel.packet.PacketBuilder;
import main.astraeus.core.net.channel.protocol.codec.game.ByteOrder;
import main.astraeus.core.net.channel.protocol.codec.game.ByteValue;

/**
 * The {@link OutgoingPacket} that updates a region for a player.
 * 
 * @author SeVen
 */
public class SendRegionUpdate extends OutgoingPacket {

	/**
	 * Creates a new {@link SendRegionUpdate}.
	 */
	public SendRegionUpdate() {
		super(73, 5);
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		builder.putShort(player.getPosition().getRegionalX() + 6,
				ByteValue.ADDITION, ByteOrder.BIG);
		builder.putShort(player.getPosition().getRegionalY() + 6);
		player.getLastPosition().setPosition(player.getPosition());
		return builder;
	}

}
