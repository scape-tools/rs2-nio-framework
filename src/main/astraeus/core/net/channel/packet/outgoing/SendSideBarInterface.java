package main.astraeus.core.net.channel.packet.outgoing;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.channel.packet.OutgoingPacket;
import main.astraeus.core.net.channel.packet.PacketBuilder;
import main.astraeus.core.net.channel.protocol.codec.game.ByteValue;

/**
 * Adds a side-bar interface onto a players game-frame.
 * 
 * @author SeVen
 */
public class SendSideBarInterface extends OutgoingPacket {
	
	/**
	 * The id of the interface to add as a side-bar interface.
	 */
	private final int interfaceId;
	
	/**
	 * The id of the tab to set the interface on.
	 */
	private final int tabId;
	
	/**
	 * Creates a new {@link SendSideBarInterface}.
	 * 
	 * @param tabId
	 * 		The tab to display the interface on.
	 * 
	 * @param interfaceId
	 * 		The interface that will be displayed.
	 */
	public SendSideBarInterface(int tabId, int interfaceId) {
		super(71, 4);
		this.interfaceId = interfaceId;
		this.tabId = tabId;
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		builder.putShort(interfaceId);
		builder.put(tabId, ByteValue.ADDITION);
		return builder;
	}

}
