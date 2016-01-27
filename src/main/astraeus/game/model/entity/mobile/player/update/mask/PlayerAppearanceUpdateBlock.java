package main.astraeus.game.model.entity.mobile.player.update.mask;

import java.nio.ByteBuffer;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.net.packet.PacketWriter;
import main.astraeus.net.protocol.codec.ByteModification;
import main.astraeus.utility.Utilities;

public class PlayerAppearanceUpdateBlock extends PlayerUpdateBlock {

      public PlayerAppearanceUpdateBlock() {
            super(0x10, UpdateFlag.APPEARANCE);
      }

      @Override
      public void encode(Player entity, PacketWriter buffer) {
            final PacketWriter properties = new PacketWriter(ByteBuffer.allocate(128));
            properties.write(entity.getAppearance().getGender().getIndicator());
            properties.write(0);
            properties.write(0);
            properties.write(0);
            properties.write(0);
            properties.write(0);
            properties.writeShort(0x100 + entity.getAppearance().getAppearanceIndices()[0]);
            properties.write(0);
            properties.writeShort(0x100 + entity.getAppearance().getAppearanceIndices()[1]);
            properties.writeShort(0x100 + entity.getAppearance().getAppearanceIndices()[2]);
            properties.writeShort(0x100 + entity.getAppearance().getAppearanceIndices()[3]);
            properties.writeShort(0x100 + entity.getAppearance().getAppearanceIndices()[4]);
            properties.writeShort(0x100 + entity.getAppearance().getAppearanceIndices()[5]);

            if (entity.getAppearance().getGender().getIndicator() == 0) {
                  properties.writeShort(0x100 + entity.getAppearance().getAppearanceIndices()[6]);
            } else {
                  properties.write(0);
            }
            properties.write(entity.getAppearance().getColorIndices()[0]);
            properties.write(entity.getAppearance().getColorIndices()[1]);
            properties.write(entity.getAppearance().getColorIndices()[2]);
            properties.write(entity.getAppearance().getColorIndices()[3]);
            properties.write(entity.getAppearance().getColorIndices()[4]);
            properties.writeShort(0x328);
            properties.writeShort(0x337);
            properties.writeShort(0x333);
            properties.writeShort(0x334);
            properties.writeShort(0x335);
            properties.writeShort(0x336);
            properties.writeShort(0x338);
            properties.writeLong(Utilities.convertStringToLong(entity.getDetails().getUsername()));
            properties.write(3);
            properties.writeShort(0);
            buffer.write(properties.getBuffer().position(), ByteModification.NEGATION);
            buffer.writeBytes(properties.getBuffer());
      }

}
