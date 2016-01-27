package main.astraeus.core.game.model.entity.mobile.player.update.mask;

import java.nio.ByteBuffer;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.protocol.codec.ByteModification;
import main.astraeus.utility.Utilities;

public class PlayerAppearanceUpdateBlock extends PlayerUpdateBlock {

      public PlayerAppearanceUpdateBlock() {
            super(0x10, UpdateFlag.APPEARANCE);
      }

      @Override
      public void encode(Player entity, PacketBuilder buffer) {
            final PacketBuilder properties = new PacketBuilder(ByteBuffer.allocate(128));
            properties.put(entity.getAppearance().getGender().getIndicator());
            properties.put(0);
            properties.put(0);
            properties.put(0);
            properties.put(0);
            properties.put(0);
            properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[0]);
            properties.put(0);
            properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[1]);
            properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[2]);
            properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[3]);
            properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[4]);
            properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[5]);

            if (entity.getAppearance().getGender().getIndicator() == 0) {
                  properties.putShort(0x100 + entity.getAppearance().getAppearanceIndices()[6]);
            } else {
                  properties.put(0);
            }
            properties.put(entity.getAppearance().getColorIndices()[0]);
            properties.put(entity.getAppearance().getColorIndices()[1]);
            properties.put(entity.getAppearance().getColorIndices()[2]);
            properties.put(entity.getAppearance().getColorIndices()[3]);
            properties.put(entity.getAppearance().getColorIndices()[4]);
            properties.putShort(0x328);
            properties.putShort(0x337);
            properties.putShort(0x333);
            properties.putShort(0x334);
            properties.putShort(0x335);
            properties.putShort(0x336);
            properties.putShort(0x338);
            properties.putLong(Utilities.convertStringToLong(entity.getDetails().getUsername()));
            properties.put(3);
            properties.putShort(0);
            buffer.put(properties.getBuffer().position(), ByteModification.NEGATION);
            buffer.putBytes(properties.getBuffer());
      }

}
