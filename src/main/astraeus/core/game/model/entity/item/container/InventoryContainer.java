package main.astraeus.core.game.model.entity.item.container;

import main.astraeus.core.game.model.entity.item.Item;
import main.astraeus.core.game.model.entity.item.ItemContainer;
import main.astraeus.core.game.model.entity.item.ItemDefinition;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.channel.packet.outgoing.ChatBoxMessagePacket;
import main.astraeus.core.net.channel.packet.outgoing.ItemOnInterfacePacket;

public class InventoryContainer extends ItemContainer {
	
	private final Player player;
	
	public InventoryContainer(Player player) {
		super(28, StackType.NORMAL);
		this.player = player;
	}

	@Override
	public void addItem(Item item) {
		
		if (!canHoldItem(player, item.getId())) {
			getPlayer().write(new ChatBoxMessagePacket("You don't have the required inventory space to hold this item."));
			return;
		}

		for (int slot = 0; slot < getSize(); slot ++) {
			
			if (getItems()[slot] == null) {
				continue;

			}
			if (getItems()[slot].getId() == item.getId()) {

				if (ItemDefinition.getDefinitions()[item.getId()].isStackable()) {
					getItems()[slot].setAmount(getItems()[slot].getAmount() + item.getAmount());
					updateContainer();
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
			updateContainer();
			return;
		}

		for (int slot = 0; slot < getSize(); slot ++) {

			if (getItems()[slot] == null) {
				getItems()[slot] = item;
				updateContainer();
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
					updateContainer();
					break;
				}
				getItems()[slot] = new Item(-1, 0);
				getItems()[slot] = null;
				updateContainer();
				deleteCount ++;
			}
		}
	}
	
	@Override
	public void updateContainer() {
		getPlayer().write(new ItemOnInterfacePacket(3214, getItems()));
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

}
