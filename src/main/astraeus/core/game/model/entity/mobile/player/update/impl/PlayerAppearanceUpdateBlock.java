package main.astraeus.core.game.model.entity.mobile.player.update.impl;

import java.nio.ByteBuffer;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.channel.packet.PacketBuilder;
import main.astraeus.core.net.channel.protocol.codec.game.ByteValue;
import main.astraeus.utility.Utilities;

public class PlayerAppearanceUpdateBlock extends PlayerUpdateBlock {

	public PlayerAppearanceUpdateBlock() {
		super(0x10, UpdateFlag.APPEARANCE);
	}

	@Override
	public void encode(Player entity, PacketBuilder buffer) {
		final PacketBuilder properties = new PacketBuilder(ByteBuffer.allocate(128));				
		properties.putByte(entity.getAppearance().getGender().getIndicator());
		properties.putByte(-1); //headicon
		properties.putByte(-1); // skull
		properties.putByte(0);
		properties.putByte(0);
		properties.putByte(0);
		properties.putByte(0);
		properties.putByte(0);
		properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[0]);
		properties.putByte(0);
		properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[1]);
		properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[2]);
		properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[3]);
		properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[4]);
		properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[5]);
		
		if (entity.getAppearance().getGender().getIndicator() == 0) {
			properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[6]);
		} else {
			properties.putByte(0);
		}
		properties.putByte(entity.getAppearance().getColorIndices()[0]);
		properties.putByte(entity.getAppearance().getColorIndices()[1]);
		properties.putByte(entity.getAppearance().getColorIndices()[2]);
		properties.putByte(entity.getAppearance().getColorIndices()[3]);
		properties.putByte(entity.getAppearance().getColorIndices()[4]);
		properties.putShort(0x328);
		properties.putShort(0x337);
		properties.putShort(0x333);
		properties.putShort(0x334);
		properties.putShort(0x335);
		properties.putShort(0x336);
		properties.putShort(0x338);
		properties.putLong(Utilities.convertStringToLong(entity.getDetails().getUsername()));
		properties.putByte(3);
		properties.putShort(0);
		buffer.putByte(properties.getBuffer().position(), ByteValue.NEGATION);
		buffer.putBytes(properties.getBuffer());		
	}

}
