package com.astraeus.core.net.channel.packet.incoming;

import java.util.HashMap;
import java.util.Map;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.IncomingPacket;
import com.astraeus.core.net.channel.packet.incoming.impl.ButtonClickPacketListener;
import com.astraeus.core.net.channel.packet.incoming.impl.CommandPacketListener;
import com.astraeus.core.net.channel.packet.incoming.impl.DialoguePacketListener;
import com.astraeus.core.net.channel.packet.incoming.impl.MoveItemPacketListener;
import com.astraeus.core.net.channel.packet.incoming.impl.MovementPacketListener;
import com.astraeus.core.net.channel.packet.incoming.impl.ObjectActionPacketListener;
import com.astraeus.core.net.channel.packet.incoming.impl.RegionalUpdatePacketListener;
import com.astraeus.core.net.channel.packet.incoming.impl.SilentPacketListener;

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
		registerPacket(new MovementPacketListener());
		registerPacket(new DialoguePacketListener());
		registerPacket(new ObjectActionPacketListener());
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
