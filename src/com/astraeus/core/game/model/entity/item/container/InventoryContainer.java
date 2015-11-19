package com.astraeus.core.game.model.entity.item.container;

import com.astraeus.core.game.model.entity.item.Item;
import com.astraeus.core.game.model.entity.item.ItemContainer;
import com.astraeus.core.game.model.entity.item.ItemDefinition;
import com.astraeus.core.game.model.entity.mobile.player.Player;

public class InventoryContainer extends ItemContainer {
	
	public InventoryContainer(Player player) {
		super(player, 28);
	}

	@Override
	public void addItem(Item item) {
		
		if (!canHoldItem(item.getId())) {
			getPlayer().getPacketSender().sendMessage("You don't have the required inventory space to hold this item.");
			return;
		}

		for (int slot = 0; slot < getSize(); slot ++) {
			
			if (getItems()[slot] == null) {
				continue;

			}
			if (getItems()[slot].getId() == item.getId()) {

				if (ItemDefinition.getDefinitions()[item.getId()].isStackable()) {
					getItems()[slot].setAmount(getItems()[slot].getAmount() + item.getAmount());
					update();
					return;
				}
			}
		}

		if (!ItemDefinition.getDefinitions()[item.getId()].isStackable() && item.getAmount() > 1) {

			int itemAmount = item.getAmount();

			for (int amount = 0; amount < itemAmount; amount ++) {
				for (int slot = 0; slot < getSize(); slot ++) {

					if (getItems()[slot] == null) {
						getItems()[slot] = item;
						item.setAmount(1);
						break;
					}
				}
			}
			update();
			return;
		}

		for (int slot = 0; slot < getSize(); slot ++) {

			if (getItems()[slot] == null) {
				getItems()[slot] = item;
				update();
				break;
			}
		}	
		
	}

	@Override
	public void removeItem(int id, int amount) {
		int deleteCount = 0;

		for (int slot = 0; slot < getSize(); slot ++) {

			if (getItems()[slot] == null) {
				continue;
			}

			if (getItems()[slot].getId() == id) {
				
				if (deleteCount == amount) {
					break;
				}

				if (getItems()[slot].getAmount() > amount && ItemDefinition.getDefinitions()[id].isStackable()) {
					getItems()[slot].setAmount(getItems()[slot].getAmount() - amount);
					update();
					break;
				}
				getItems()[slot] = new Item(-1, 0);
				getItems()[slot] = null;
				update();
				deleteCount ++;
			}
		}
	}
	
	@Override
	public void update() {
		getPlayer().getPacketSender().sendItemsOnInterface(3214, getItems());
	}

}
