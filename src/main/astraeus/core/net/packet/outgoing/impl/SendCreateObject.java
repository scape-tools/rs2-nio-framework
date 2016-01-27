package main.astraeus.core.net.packet.outgoing.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.object.GameObject;
import main.astraeus.core.net.packet.PacketWriter;
import main.astraeus.core.net.packet.outgoing.OutgoingPacket;

/**
 * The {@link OutgoingPacket} that creates a new object in the game world.
 * 
 * @author SeVen
 */
public class SendCreateObject extends OutgoingPacket {

	/**
	 * The object to create in the game world.
	 */
	@SuppressWarnings("unused")
	private final GameObject object;
	
	/**
	 * Creates a new {@link SendCreateObject}.
	 * 
	 * @param object
	 * 		The object create.
	 */
	public SendCreateObject(GameObject object) {
		super(151, 5);
		this.object = object;
	}

	@Override
	public PacketWriter encode(Player player) {
//		player.getContext().prepare(this, writer);
//		writer.put(0, ByteValue.SUBTRACTION);
//		writer.putShort(object.getId(), ByteOrder.LITTLE);
//		writer.putByte((object.getType() << 2) + (object.getFacingDirection().getDirection() & 3), ByteValue.SUBTRACTION);
		return writer;
	}

}
