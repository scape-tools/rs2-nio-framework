package main.astraeus.core.net.channel.packet.outgoing;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.object.GameObject;
import main.astraeus.core.net.channel.packet.OutgoingPacket;
import main.astraeus.core.net.channel.packet.PacketBuilder;
import main.astraeus.core.net.channel.packet.PacketHeader;
import main.astraeus.core.net.channel.protocol.codec.game.ByteOrder;
import main.astraeus.core.net.channel.protocol.codec.game.ByteValue;

/**
 * The {@link OutgoingPacket} that creates a new object in the game world.
 * 
 * @author SeVen
 */
public class CreateObjectPacket extends OutgoingPacket {

	/**
	 * The object to create in the game world.
	 */
	private final GameObject object;
	
	/**
	 * Creates a new {@link CreateObjectPacket}.
	 * 
	 * @param object
	 * 		The object create.
	 */
	public CreateObjectPacket(GameObject object) {
		super(151, PacketHeader.STANDARD, 5);
		this.object = object;
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		builder.put(0, ByteValue.SUBTRACTION);
		builder.putShort(object.getId(), ByteOrder.LITTLE);
		builder.putByte((object.getType() << 2) + (object.getFacing().getDirection() & 3), ByteValue.SUBTRACTION);
		return builder;
	}

}
