package main.astraeus.net.packet.incoming;

import java.util.HashMap;
import java.util.Map;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.net.packet.incoming.impl.ButtonClickPacketListener;
import main.astraeus.net.packet.incoming.impl.CommandPacketListener;
import main.astraeus.net.packet.incoming.impl.DialoguePacketListener;
import main.astraeus.net.packet.incoming.impl.MoveItemPacketListener;
import main.astraeus.net.packet.incoming.impl.ObjectInteractionPacketListener;
import main.astraeus.net.packet.incoming.impl.RegionalUpdatePacketListener;
import main.astraeus.net.packet.incoming.impl.SilentPacketListener;
import main.astraeus.net.packet.incoming.impl.WalkingPacketListener;

/**
 * @author Dylan Vicchiarelli
 *
 * Handles the registration and operation of incoming packets.
 */
public final class IncomingPacketRegistration {

	/**
	 * A collection of the incoming packets that where registered during initialization.
	 */
	private final static Map<Integer, IncomingPacketListener> INCOMING_PACKETS = new HashMap<>();

	/**
	 * The default class constructor. Populates the packet collection
	 * upon class reference.
	 */
	public IncomingPacketRegistration() {
		registerPacket(new ButtonClickPacketListener());
		registerPacket(new CommandPacketListener());
		registerPacket(new WalkingPacketListener());
		registerPacket(new DialoguePacketListener());
		registerPacket(new ObjectInteractionPacketListener());
		registerPacket(new SilentPacketListener());
		registerPacket(new RegionalUpdatePacketListener());
		registerPacket(new MoveItemPacketListener());
	}

	/**
	 * Registers the annotated opcode(s) possessed by the packet listener.
	 * 
	 * @param listener The listener implementation - the owner of the annotated opcodes.
	 */
	private static final void registerPacket(IncomingPacketListener listener) {
		IncomingPacketOpcode annotation = listener.getClass().getAnnotation(IncomingPacketOpcode.class);
		if (annotation != null) {
			for (int opcode : annotation.value()) {
				INCOMING_PACKETS.put(opcode, listener);
			}
		}
	}

	/**
	 * Handles a packet request sent by the RuneScape client. Once the
	 * request has been received it is dispatched to the corresponding listener.
	 * 
	 * @param packet The packet being handled.
	 * 
	 * @param player The player association.
	 */
	public static final void dispatchToListener(IncomingPacket packet, Player player) {
		IncomingPacketListener listener = INCOMING_PACKETS.get(packet.getOpcode());
		if (listener != null) {
			listener.handleMessage(player, packet);
		}
	}
}
