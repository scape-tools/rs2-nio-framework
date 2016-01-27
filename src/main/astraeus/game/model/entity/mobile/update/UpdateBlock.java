package main.astraeus.game.model.entity.mobile.update;

import main.astraeus.game.model.entity.mobile.MobileEntity;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.net.packet.PacketWriter;

/**
 * Represents an update block of the entity updating procedure.
 * 
 * @author SeVen
 */
public abstract class UpdateBlock<E extends MobileEntity> {

      /**
       * The mask to identify the update.
       */
      private final int mask;

      /**
       * The enumerated update type.
       */
      private final UpdateFlag flag;

      /**
       * Creates a new {@link UpdateBlock}.
       * 
       * @param flag The enumerated update type.
       */
      public UpdateBlock(int mask, UpdateFlag flag) {
            this.mask = mask;
            this.flag = flag;
      }

      /**
       * Encodes an update to a buffer to be later appended to the main update block.
       * 
       * @param entity The entity to write.
       * 
       * @param buffer The buffer that will store the data.
       */
      public abstract void encode(E entity, PacketWriter builder);

      /**
       * Gets the mask.
       * 
       * @return mask
       */
      public int getMask() {
            return mask;
      }

      /**
       * Gets the enumerated update type.
       * 
       * @return flag
       */
      public UpdateFlag getFlag() {
            return flag;
      }

}
