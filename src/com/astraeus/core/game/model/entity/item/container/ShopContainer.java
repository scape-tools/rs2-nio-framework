package com.astraeus.core.game.model.entity.item.container;

import com.astraeus.core.game.model.entity.item.Item;
import com.astraeus.core.game.model.entity.item.ItemContainer;
import com.astraeus.core.game.model.entity.mobile.player.Player;

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
	public void update() {
		
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

}
