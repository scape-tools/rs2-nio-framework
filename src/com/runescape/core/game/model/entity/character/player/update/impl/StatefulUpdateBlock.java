package com.runescape.core.game.model.entity.character.player.update.impl;

import com.runescape.core.game.model.entity.UpdateFlags;
import com.runescape.core.game.model.entity.character.player.Player;
import com.runescape.core.game.model.entity.character.player.update.UpdateBlock;
import com.runescape.core.net.ByteValue;
import com.runescape.core.net.channel.message.PacketBuilder;

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
				buffer.putByte((mask & 0xFF), ByteValue.STANDARD);
				buffer.putByte((mask >> 8), ByteValue.STANDARD);
			} else {
				buffer.putByte((mask), ByteValue.STANDARD);
			}
			if (player.getUpdateFlags().contains(UpdateFlags.UPDATE_APPEARANCE) || forceful) {
				final PacketBuilder properties = new PacketBuilder();
				
				properties.allocate(128);
				
				properties.putByte(player.getAppearance().getGender().getIndicator(), ByteValue.STANDARD);
				properties.putByte(0, ByteValue.STANDARD);
				properties.putByte(0, ByteValue.STANDARD);
				properties.putByte(0, ByteValue.STANDARD);
				properties.putByte(0, ByteValue.STANDARD);
				properties.putByte(0, ByteValue.STANDARD);
				properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[0], ByteValue.STANDARD);
				properties.putByte(0, ByteValue.STANDARD);
				properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[1], ByteValue.STANDARD);
				properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[2], ByteValue.STANDARD);
				properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[3], ByteValue.STANDARD);
				properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[4], ByteValue.STANDARD);
				properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[5], ByteValue.STANDARD);
				
				if (player.getAppearance().getGender().getIndicator() == 0) {
					properties.putShort(0x100 + player.getAppearance().getAppearanceIndices()[6], ByteValue.STANDARD);
				} else {
					properties.putByte(0, ByteValue.STANDARD);
				}
				properties.putByte(player.getAppearance().getColorIndices()[0], ByteValue.STANDARD);
				properties.putByte(player.getAppearance().getColorIndices()[1], ByteValue.STANDARD);
				properties.putByte(player.getAppearance().getColorIndices()[2], ByteValue.STANDARD);
				properties.putByte(player.getAppearance().getColorIndices()[3], ByteValue.STANDARD);
				properties.putByte(player.getAppearance().getColorIndices()[4], ByteValue.STANDARD);
				properties.putShort(0x328, ByteValue.STANDARD);
				properties.putShort(0x337, ByteValue.STANDARD);
				properties.putShort(0x333, ByteValue.STANDARD);
				properties.putShort(0x334, ByteValue.STANDARD);
				properties.putShort(0x335, ByteValue.STANDARD);
				properties.putShort(0x336, ByteValue.STANDARD);
				properties.putShort(0x338, ByteValue.STANDARD);
				properties.putLong(0, ByteValue.STANDARD);
				properties.putByte(3, ByteValue.STANDARD);
				properties.putShort(0, ByteValue.STANDARD);
				buffer.putByte(properties.getInternal().position(), ByteValue.NEGATED);
				buffer.putBytes(properties.getInternal());
			}
		}
	}
}