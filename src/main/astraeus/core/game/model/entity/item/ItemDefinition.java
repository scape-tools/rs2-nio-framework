package main.astraeus.core.game.model.entity.item;

import main.astraeus.core.game.GameConstants;

/**
 * The class that defines an in-game {@link Item}.
 * 
 * @author SeVen
 */
public class ItemDefinition {
	
	/**
	 * The array of definitions for items in-game.
	 */
	public static final ItemDefinition[] DEFINITIONS = new ItemDefinition[GameConstants.MAXIMUM_ITEMS];
		
	/**
	 * An enumeration that represents the type of wield an {@link ItemDefinition} has.
	 * The position in the enumeration {@link Enum#ordinal()} represents the position
	 * the item holds in the equipment slot.
	 *
	 * @author Dustin Greyfield
	 *
	 */
	public enum WieldType {
		/**
		 * A wield type that represents an item in the hat (head) slot.
		 */
		HAT(0),
		
		/**
		 * A wield type that represents an item in the cape (back) slot.
		 */
		CAPE(1),
		
		/**
		 * A wield type that represents an item in the shield slot.
		 */
		SHIELD(5),
		
		/**
		 * A wield type that represents an item in the gloves (hands) slot.
		 */
		GLOVES(9),
		
		/**
		 * A wield type that represents an item in the boots (feet) slot.
		 */
		BOOTS(10),
		
		/**
		 * A wield type that represents an item in the amulet (neck) slot.
		 */
		AMULET(2),
		
		/**
		 * A wield type that represents an item in the ring (finger) slot.
		 */
		RING(12),
		
		/**
		 * A wield type that represents an item in the arrow (projectile) slot.
		 */
		ARROWS(13),
		
		/**
		 * A wield type that represents an item in the body slot.
		 */
		BODY(4),
		
		/**
		 * A wield type that represents an item in the legs slot.
		 */
		LEGS(7),
		
		/**
		 * A wield type that represents an item in the weapon (hand) slot.
		 */
		WEAPON(3);
		
		/**
		 * The slot in which an item will be contained
		 */
		private final int slot;
		
		WieldType(final int slot) {
			this.slot = slot;
		}
		
		public int getSlot() {
			return this.slot;
		}
		
	}
	
	/**
	 * The id of the item
	 */
	private final int id;
	
	/**
	 * The name of the item
	 */
	private final String name;
	
	/**
	 * The description of the item
	 */
	private final String description;
	
	/**
	 * The flag determining if an item is tradeable
	 */
	private final boolean untradeable;
	
	/**
	 * The flag determining whether an item is destroyable
	 */
	private final boolean destroyable;
	
	/**
	 * The flag representing whether an item is stackable
	 */
	private final boolean stackable;
	
	/**
	 * The item's value
	 */
	private final int value;
	
	/**
	 * The price for special stores
	 */
	private final int specialPrice;
	
	/**
	 * The items low alchemy value
	 */
	private final int lowAlchemy;
	
	/**
	 * The item's high alchemy value
	 */
	private final int highAlchemy;
	
	/**
	 * The item's weight
	 */
	private final double weight;
	
	/**
	 * The flag determing if an item is noted
	 */
	private final boolean noted;
	
	/**
	 * the flag representing whether the item is noteable
	 */
	private final boolean noteable;
	
	/**
	 * The noted id of an item
	 */
	private final int notedId;
	
	/**
	 * The unnoted id of an item
	 */
	private final int parentId;
	
	/**
	 * The flag representing if an item is 2 handed
	 */
	private final boolean isTwoHanded;
	
	/**
	 * The flag representing the equipment type of an item
	 */
	private final WieldType equipmentType;
	
	/**
	 * The flag representing if an item is a weapon
	 */
	private final boolean weapon;
	
	/**
	 * A flag representing if this is a full body item
	 */
	private final boolean fullBody;
	
	/**
	 * A flag representing if this is a full hat item
	 */
	private final boolean fullHat;
	
	/**
	 * A flag representing if this is a full mask
	 */
	private final boolean fullMask;
	
	/**
	 * The bonuses of the item
	 */
	private final double[] bonus;
	
	/**
	 * The actions this item has
	 */
	private final String[] actions;

	/**
	 * Constructs a new {@link ItemDefinition}.
	 */
	public ItemDefinition(final int id, final String name, final String description,
			final boolean untradeable, final boolean destroyable, final boolean stackable,
			final int value, final int specialPrice, final int lowAlchemy, final int highAlchemy, final double weight, 
			final boolean noted, final boolean noteable, final int childId, final int parentId,
			final boolean isTwoHanded, final WieldType equipmentType, final boolean weapon, 
			final boolean fullBody, final boolean fullHat, final boolean fullMask, final double[] bonus,
			final String[] actions) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.untradeable = untradeable;
		this.destroyable = destroyable;
		this.stackable = stackable;
		this.value = value;
		this.specialPrice = specialPrice;
		this.lowAlchemy = lowAlchemy;
		this.highAlchemy = highAlchemy;
		this.weight = weight;
		this.noted = noted;
		this.noteable = noteable;
		this.notedId = childId;
		this.parentId = parentId;
		this.isTwoHanded = isTwoHanded;
		this.weapon = weapon;
		this.equipmentType = equipmentType;
		this.fullBody = fullBody;
		this.fullHat = fullHat;
		this.fullMask = fullMask;
		this.bonus = bonus;
		this.actions = actions;
	}
	
	/**
	 * @return the definitions
	 */
	public static ItemDefinition[] getDefinitions() {
		return DEFINITIONS;
	}

	/**
	 *  @return - id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 *  @return - name
	 */
	public String getName() {
		return name;
	}

	/**
	 *  @return - description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 *  @return - tradeable
	 */
	public boolean isUntradeable() {
		return untradeable;
	}

	/**
	 *  @return - destroyable
	 */
	public boolean isDestroyable() {
		return destroyable;
	}

	/**
	 *  @return - stackable
	 */
	public boolean isStackable() {
		return stackable;
	}

	/**
	 *  @return - value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @return the specialPrice
	 */
	public int getSpecialPrice() {
		return specialPrice;
	}

	/**
	 *  @return - lowAlchemy
	 */
	public int getLowAlchemy() {
		return lowAlchemy;
	}

	/**
	 *  @return - highAlchemy
	 */
	public int getHighAlchemy() {
		return highAlchemy;
	}

	/**
	 *  @return - weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 *  @return - noted
	 */
	public boolean isNoted() {
		return noted;
	}

	/**
	 * @return - noteable
	 */
	public boolean isNoteable() {
		return noteable;
	}

	/**
	 * @return - childId
	 */
	public int getNotedId() {
		return notedId;
	}

	/**
	 * @return - parentId
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 *  @return - isTwoHanded
	 */
	public boolean isTwoHanded() {
		return isTwoHanded;
	}

	/**
	 *  @return - equipmentType
	 */
	public WieldType getEquipmentType() {
		return equipmentType;
	}

	/**
	 *  @return - weapon
	 */
	public boolean isWeapon() {
		return weapon;
	}

	/**
	 * @return the fullBody
	 */
	public boolean isFullBody() {
		return fullBody;
	}

	/**
	 * @return the fullHat
	 */
	public boolean isFullHat() {
		return fullHat;
	}

	/**
	 * @return the fullMask
	 */
	public boolean isFullMask() {
		return fullMask;
	}

	/**
	 *  @return - bonus
	 */
	public double[] getBonus() {
		return bonus;
	}

	/**
	 *  @return - actions
	 */
	public String[] getActions() {
		return actions;
	}
	
	/**
	 * Checks if an item definition is wield-able.
	 * 
	 * {@code true} If this definition is wield-able. {@code false} otherwise.
	 */
	public boolean isWieldable() {
		String menu = actions[1].toLowerCase();
		return (menu.equals("wear") || menu.equals("wield") || menu.equals("equip"));
	}
	
	@Override
	public String toString() {
		return "Item [" + id + "," + name + "]";
	}
}
