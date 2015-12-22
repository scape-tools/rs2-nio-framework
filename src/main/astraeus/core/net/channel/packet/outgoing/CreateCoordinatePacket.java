package main.astraeus.core.net.channel.packet.outgoing;

import main.astraeus.core.game.model.entity.Position;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.channel.packet.OutgoingPacket;
import main.astraeus.core.net.channel.packet.PacketBuilder;
import main.astraeus.core.net.channel.packet.PacketHeader;
import main.astraeus.core.net.channel.protocol.codec.game.ByteValue;

/**
 * The {@link OutgoingPacket} that creates a new coordinate.
 * 
 * @author SeVen
 */
public class CreateCoordinatePacket extends OutgoingPacket {
	
	/**
	 * The coordinate to create.
	 */
	private final Position coordinate;

	/**
	 * Creates a new {@link CreateCoordinatePacket}.
	 * 
	 * @param coordinate
	 * 		The new coordinate.
	 */
	public CreateCoordinatePacket(Position coordinate) {
		super(85, PacketHeader.STANDARD, 3);
		this.coordinate = coordinate;
	}

	@Override
	public PacketBuilder dispatch(Player player) {		
		player.getContext().prepare(this, builder);
		builder.putByte(coordinate.getY() -  8 * player.getLastPosition().getRegionalY() , ByteValue.INVERSION);
		builder.putByte(coordinate.getX() -  8 * player.getLastPosition().getRegionalX() , ByteValue.INVERSION);
		return builder;
	}

}
