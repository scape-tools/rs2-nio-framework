package main.astraeus.core.game.model.entity.item.container;

import main.astraeus.core.game.model.entity.item.Item;
import main.astraeus.core.game.model.entity.item.ItemContainer;
import main.astraeus.core.game.model.entity.mobile.player.Player;

public class ShopContainer extends ItemContainer {

	private final Player player;
	
	public ShopContainer(Player player) {
		super(40, StackType.ALWAYS_STACK);
		this.player = player;
	}

	@Override
	public void addItem(Item item) {
		
	}

	@Override
	public void removeItem(int id, int amount) {
		
	}

	@Override
	public void updateContainer() {
		
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

}
