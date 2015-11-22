package com.astraeus.core.game.model.entity.mobile.player.update.impl;

import com.astraeus.core.game.model.entity.UpdateFlags;
import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.model.entity.mobile.player.update.UpdateBlock;
import com.astraeus.core.net.channel.message.PacketBuilder;
import com.astraeus.core.net.channel.protocol.codec.game.ByteValue;
import com.astraeus.core.utility.Utilities;

public final class StatefulUpdateBlock extends UpdateBlock {

	/**
	 * If the update was forced.
	 */
	private final boolean forceful;

	/**
	 * The overloaded class constructor used for instantiation of this
	 * class file.
	 * 
	 * @param forceful If the update was forced.
	 */
	public StatefulUpdateBlock(boolean forceful) {
		this.forceful = forceful;
	}

	@Override
	public void update(Player player, PacketBuilder buffer) {		
		synchronized(player) {
			/*
			 * The mask to denote specific updates.
			 */
			int mask = 0x0;
			if (player.getUpdateFlags().contains(UpdateFlags.UPDATE_APPEARANCE) || forceful) {
				mask |= 0x10;
			}
			if (mask >= 0x100) {
				mask |= 0x40;
				buffer.putByte((mask & 0xFF));
				buffer.putByte((mask >> 8));
			} else {
				buffer.putByte((mask));
			}
			if (player.getUpdateFlags().contains(UpdateFlags.UPDATE_APPEARANCE) || forceful) {
				final PacketBuilder properties = new PacketBuilder();
				
				properties.allocate(128);
				
				properties.putByte(player.getAppearance().getGender().getIndicator());
				properties.putByte(0);
				properties.putByte(0);
				properties.putByte(0);
				properties.putByte(0);
				properties.putByte(0);
				properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[0]);
				properties.putByte(0);
				properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[1]);
				properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[2]);
				properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[3]);
				properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[4]);
				properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[5]);
				
				if (player.getAppearance().getGender().getIndicator() == 0) {
					properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[6]);
				} else {
					properties.putByte(0);
				}
				properties.putByte(player.getAppearance().getColorIndices()[0]);
				properties.putByte(player.getAppearance().getColorIndices()[1]);
				properties.putByte(player.getAppearance().getColorIndices()[2]);
				properties.putByte(player.getAppearance().getColorIndices()[3]);
				properties.putByte(player.getAppearance().getColorIndices()[4]);
				properties.putShort(0x328);
				properties.putShort(0x337);
				properties.putShort(0x333);
				properties.putShort(0x334);
				properties.putShort(0x335);
				properties.putShort(0x336);
				properties.putShort(0x338);
				properties.putLong(Utilities.convertStringToLong(player.getDetails().getUsername()));
				properties.putByte(3);
				properties.putShort(0);
				buffer.putByte(properties.getInternal().position(), ByteValue.INVERSION);
				buffer.putBytes(properties.getInternal());
			}
		}
	}
}