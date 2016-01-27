package main.astraeus.game.model.entity.mobile.player.update.mask;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.player.Player.Attributes;
import main.astraeus.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.net.packet.PacketWriter;
import main.astraeus.net.protocol.codec.ByteModification;
import main.astraeus.net.protocol.codec.ByteOrder;

/**
 * The {@link PlayerUpdateBlock} implementation that updates a players facing direction.
 * 
 * @author SeVen
 */
public class PlayerFaceCoordinateUpdateBlock extends PlayerUpdateBlock {

      /**
       * Creates a new {@link PlayerFaceCoordinateUpdateBlock}.
       */
      public PlayerFaceCoordinateUpdateBlock() {
            super(0x2, UpdateFlag.FACE_COORDINATE);
      }

      @Override
      public void encode(Player entity, PacketWriter builder) {
            builder.writeShort((Integer) entity.getAttributes().get(Attributes.FACING_COORDINATE_X),
                        ByteModification.ADDITION, ByteOrder.LITTLE)
                        .writeShort((Integer) entity.getAttributes()
                                    .get(Attributes.FACING_COORDINATE_Y), ByteOrder.LITTLE);
      }

}
