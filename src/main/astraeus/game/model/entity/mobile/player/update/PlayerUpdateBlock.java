package main.astraeus.game.model.entity.mobile.player.update;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.update.UpdateBlock;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;

/**
 * The {@link UpdateBlock} implementation for updating players.
 * 
 * @author SeVen
 */
public abstract class PlayerUpdateBlock extends UpdateBlock<Player> {

      /**
       * Creates a new {@link PlayerUpdateBlock}.
       * 
       * @param flag The enumerated update block.
       */
      public PlayerUpdateBlock(int mask, UpdateFlag flag) {
            super(mask, flag);
      }

}
