package com.runescape.core.net.channel.message;

import java.util.HashMap;
import java.util.Map;

import com.runescape.core.game.model.entity.mobile.player.Player;
import com.runescape.core.net.channel.message.incoming.IncomingPacketListener;
import com.runescape.core.net.channel.message.incoming.impl.ButtonClickPacketListener;
import com.runescape.core.net.channel.message.incoming.impl.CommandPacketListener;
import com.runescape.core.net.channel.message.incoming.impl.DialoguePacketListener;
import com.runescape.core.net.channel.message.incoming.impl.MovementPacketListener;

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
	public static final void dispatchToListener(Packet packet, Player player) {
		IncomingPacketListener listener = INCOMING_PACKETS.get(packet.getOpcode());
		if (listener != null) {
			listener.handleMessage(player, packet);
		}
	}
}
