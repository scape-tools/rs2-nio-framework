package main.astraeus.core.game.model.entity.mobile.update;

import java.util.BitSet;

/**
 * The class that provides functions for {@link UpdateFlag}s.
 * 
 * @author SeVen
 */
public class UpdateFlags {

	/**
	 * The bitset that will be used to store {@code true} and {@code false} values.
	 */
	private BitSet flags = new BitSet();

	/**
	 * The enumerated types of updates that can be updated.
	 */
	public enum UpdateFlag {
		
		APPEARANCE,
		CHAT,
		TELEPORTING, // not a real mask...
		REGION_CHANGING, // not a real mask...
		GRAPHICS,
		ANIMATION,
		FORCED_CHAT,
		FACE_ENTITY,
		FACE_COORDINATE,
		SINGLE_HIT,
		DOUBLE_HIT,
		TRANSFORM,
		FORCE_MOVEMENT;
		
	}

	/**
	 * Checks if any flags are {@code true} in the bitset.
	 * 
	 * @return {@code true} if there is a flag which has a value of {@code true}, return {@code false} otherwise.
	 */
	public boolean isUpdateRequired() {
		return !flags.isEmpty();
	}

	/**
	 * Sets a flag to {@code true}.
	 */
	public void flag(UpdateFlag flag) {
		flags.set(flag.ordinal(), true);
	}
	
	/**
	 * Sets a flag to {@code false}.
	 */
	public void unflag(UpdateFlag flag) {
		flags.set(flag.ordinal(), false);
	}

	/**
	 * Gets a {@code flag} from the bitset and checks its value.
	 * 
	 * @param flag
	 * 		The flag to retrieve from the bitset.
	 * 
	 * @return The value of the {@code flag} which is either {@code true} or {@code false}.
	 */
	public boolean get(UpdateFlag flag) {
		return flags.get(flag.ordinal());
	}

	/**
	 * Sets all flags in the bitset to false. 
	 */
	public void clear() {
		flags.clear();
	}
	
}

