package main.astraeus.core.net.packet.outgoing.impl;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.PacketWriter;
import main.astraeus.core.net.packet.outgoing.OutgoingPacket;

/**
 * The {@link OutgoingPacket} that shows an animation on an interface.
 * 
 * @author SeVen
 */
public class SendInterfaceAnimation extends OutgoingPacket {
	
	/**
	 * The id of the interface that is showing the animation.
	 */
	private final int interfaceId;
	
	/**
	 * The id of the animation that is showing.
	 */
	private final int animationId;

	/**
	 * Creates a new {@link SendInterfaceAnimation}.
	 * 
	 * @param interfaceId
	 * 		The id of the interface that is showing the animation.
	 * 
	 * @param animationId
	 * 		The id of the animation that is showing.
	 */
	public SendInterfaceAnimation(int interfaceId, int animationId) {
		super(200, 10);
		this.interfaceId = interfaceId;
		this.animationId = animationId;
	}

	@Override
	public PacketWriter encode(Player player) {
		player.getContext().prepare(this, writer);
		writer.writeShort(interfaceId);
		writer.writeShort(animationId);
		return writer;
	}

}
