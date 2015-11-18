package com.astraeus.core.net.channel.message.incoming.impl;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.model.entity.mobile.player.Player.Attributes;
import com.astraeus.core.game.pulse.PulseScheduler;
import com.astraeus.core.game.pulse.impl.InteractionDistancePulse;
import com.astraeus.core.game.pulse.impl.InteractionDistancePulse.InteractionType;
import com.astraeus.core.net.channel.message.IncomingPacketOpcode;
import com.astraeus.core.net.channel.message.Packet;
import com.astraeus.core.net.channel.message.incoming.IncomingPacketConstants;
import com.astraeus.core.net.channel.message.incoming.IncomingPacketListener;

/**
 * The packet opcodes which this listener implementation handles.
 */
@IncomingPacketOpcode ({ 132, 252, 70 })
public final class ObjectActionPacketListener implements IncomingPacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		switch (packet.getOpcode()) {

		case IncomingPacketConstants.FIRST_CLICK_OBJECT:
			int objectX = packet.readLittleEndianShortAddition();
			int objectIndex = packet.getBuffer().getShort();
			int objectY = packet.readAdditionalShort();

			player.getAttributes().put(Attributes.CLICK_Y, objectY);
			player.getAttributes().put(Attributes.CLICK_INDEX, objectIndex);
			player.getAttributes().put(Attributes.CLICK_X, objectX);
			PulseScheduler.getInstance().register(new InteractionDistancePulse(player, InteractionType.OBJECT_INTERACTION_FIRST_CLICK), true);
			break;
		}
		
	}

}
