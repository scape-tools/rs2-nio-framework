package main.astraeus.net.packet.outgoing.impl;

import main.astraeus.game.model.entity.item.Item;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.PacketHeader;
import main.astraeus.net.packet.PacketWriter;
import main.astraeus.net.packet.outgoing.OutgoingPacket;
import main.astraeus.net.protocol.codec.ByteModification;
import main.astraeus.net.protocol.codec.ByteOrder;

/**
 * The {@link OutgoingPacket} that sends an array of items on an interface.
 * 
 * @author SeVen
 */
public class SendItemOnInterface extends OutgoingPacket {

	/**
	 * The id of the interface to display the items on.
	 */
	private final int interfaceId;
	
	/**
	 * The items to display.
	 */
	private final Item[] items;
	
	/**
	 * Creates a new {@link SendItemOnInterface}.
	 * 
	 * @param interfaceId
	 * 		The id of the interface to display the items on.
	 * 
	 * @param items
	 * 		The items to display.
	 */
	public SendItemOnInterface(int interfaceId, Item[] items) {
		super(53, PacketHeader.VARIABLE_SHORT, 2048);
		this.interfaceId = interfaceId;
		this.items = items;
	}

	@Override
	public PacketWriter encode(Player player) {
		player.getContext().prepare(this, writer);
		writer.writeShort(interfaceId);
		writer.writeShort(items.length);
		for(Item item : items) {
			if (item != null) {
			if(item.getAmount() > 254) {
				writer.write(255);
				writer.writeInt(item.getAmount(), ByteOrder.INVERSE);				
			} else {
				writer.write(item.getAmount());
			}
			writer.writeShort(item.getId() + 1, ByteModification.ADDITION, ByteOrder.LITTLE);
			} else {
				writer.write(0);
				writer.writeShort(0, ByteModification.ADDITION, ByteOrder.LITTLE);
			}			
		}
		return writer;
	}

}
