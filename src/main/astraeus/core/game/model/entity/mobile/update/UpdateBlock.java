package main.astraeus.core.game.model.entity.mobile.update;

import main.astraeus.core.game.model.entity.mobile.MobileEntity;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.channel.packet.PacketBuilder;

/**
 * Represents an update block of the entity updating procedure.
 * 
 * @author SeVen
 */
public abstract class UpdateBlock<E extends MobileEntity>{
	
	/**
	 * The enumerated update type.
	 */	
	private final UpdateFlag flag;
	
	/**
	 * Creates a new {@link UpdateBlock}.
	 * 
	 * @param flag
	 * 		The enumerated update type.
	 */
	public UpdateBlock(UpdateFlag flag) {
		this.flag = flag;
	}
	
	/**
	 * Writes the update to a buffer to be later appended to the main update block.
	 * 
	 * @param entity
	 * 		The entity to write.
	 * 
	 * @param buffer
	 * 		The buffer that will store the data.
	 */
	public abstract void write(E entity, PacketBuilder builder);
	
	/**
	 * Gets the mask.
	 * 
	 * @return mask
	 */
	public int getMask() {
		return flag.getMask();
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
