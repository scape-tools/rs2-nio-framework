package main.astraeus.content.shop;

/**
 * An enumeration which resembles in-game currency
 * 
 * @author SeVen
 */
public enum Currency {
	
	COINS(995),
	TOKKUL(6529),
	WARRIOR_GUILD_TOKENS(8851);
	
	/**
	 * The id of this currency
	 */
	private int id;

	private Currency(int itemId) {
		this.id = itemId;
	}
	
	/**
	 * Returns the itemId of the currency.
	 */
	public int getId() {
		return id;
	}
}
