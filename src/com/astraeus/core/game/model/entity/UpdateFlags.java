package com.astraeus.core.game.model.entity;

import java.util.BitSet;

public class UpdateFlags {

	/**
	 * The bitset (flag data).
	 */
	private BitSet flags = new BitSet();

	/**
	 * Represents a single type of update flag.
	 * 
	 * @author Graham Edgecombe
	 * 
	 */
	public enum UpdateFlag {

		/**
		 * Appearance update.
		 */
		APPEARANCE(0x10),

		/**
		 * Chat update.
		 */
		CHAT(0x80),

		/**
		 * Teleporting update
		 */
		TELEPORTING(0), // not a real mask...

		/**
		 * Map region chaging update
		 */
		REGION_CHANGING(0), // not a real mask...
		
		/**
		 * Denotes an npc is registered to the local players list.
		 */
		REGISTERED_LOCALLY(0), // not a real mask...
		
		/**
		 * Graphics update.
		 */
		GRAPHICS(0x100),

		/**
		 * Animation update.
		 */
		ANIMATION(0x8),

		/**
		 * Forced chat update.
		 */
		FORCED_CHAT(0x4),

		/**
		 * Interacting entity update.
		 */
		FACE_ENTITY(0x1),

		/**
		 * Face coordinate entity update.
		 */
		FACE_COORDINATE(0x2),

		/**
		 * Hit update.
		 */
		HIT(0x20),

		/**
		 * Hit 2 update/
		 */
		HIT_2(0x200),

		/**
		 * Update flag used to transform npc to another.
		 */
		TRANSFORM(0), // not a real mask...

		/**
		 * Update flag used to signify force movement.
		 */
		FORCE_MOVEMENT(0x400);

		private final int mask;

		UpdateFlag(int mask) {
			this.mask = mask;
		}

		public int getMask() {
			return mask;
		}
	}

	/**
	 * The damage dealt
	 */
	private int damage = -1;

	/**
	 * The hitmask to the attack
	 */
	private int hitMask = -1;

	/**
	 * Checks if an update required.
	 * 
	 * @return <code>true</code> if 1 or more flags are set, <code>false</code>
	 *         if not.
	 */
	public boolean isUpdateRequired() {
		return !flags.isEmpty();
	}

	/**
	 * Flags (sets to true) a flag.
	 * 
	 * @param flag
	 *            The flag to flag.
	 */
	public void flag(UpdateFlag flag) {
		flags.set(flag.ordinal(), true);
	}

	/**
	 * Sets a flag.
	 * 
	 * @param flag
	 *            The flag.
	 * @param value
	 *            The value.
	 */
	public void set(UpdateFlag flag, boolean value) {
		flags.set(flag.ordinal(), value);
	}

	/**
	 * Gets the value of a flag.
	 * 
	 * @param flag
	 *            The flag to get the value of.
	 * @return The flag value.
	 */
	public boolean get(UpdateFlag flag) {
		return flags.get(flag.ordinal());
	}

	/**
	 * Resest all update flags.
	 */
	public void clear() {
		flags.clear();
	}

	/**
	 * Gets the damage dealt to the opposing entity
	 * 
	 * @return
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Sets the damage to the opposing entity
	 * 
	 * @param damage
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * Sets the hitmask to send
	 * 
	 * @param mask
	 */
	public void setHitMask(int mask) {
		this.hitMask = mask;
	}

	/**
	 * Gets the hitmask
	 * 
	 * @return
	 */
	public int getHitmask() {
		return hitMask;
	}
	
}