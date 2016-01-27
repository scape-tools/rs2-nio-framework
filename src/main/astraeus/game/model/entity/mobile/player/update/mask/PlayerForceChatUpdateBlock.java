package main.astraeus.game.model.entity.mobile.player.update.mask;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.net.packet.PacketWriter;

/**
 * The {@link PlayerUpdateBlock} implementation that updates a players forced text.
 * 
 * @author SeVen
 */
public class PlayerForceChatUpdateBlock extends PlayerUpdateBlock {

      /**
       * Creates a new {@link PlayerForceChatUpdateBlock}.
       */
      public PlayerForceChatUpdateBlock() {
            super(0x4, UpdateFlag.FORCED_CHAT);
      }

      @Override
      public void encode(Player entity, PacketWriter builder) {
            builder.writeString(entity.getForcedChat());
      }

}
