package main.astraeus.core.game.model.entity.item.container;

import main.astraeus.core.Configuration;
import main.astraeus.core.game.model.entity.item.Item;
import main.astraeus.core.game.model.entity.item.ItemContainer;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.outgoing.impl.SendInventoryInterface;
import main.astraeus.core.net.packet.outgoing.impl.SendString;

public class DepositContainer extends ItemContainer {

	private final Player player;
	
	public DepositContainer(Player player) {
		super(28, StackType.ALWAYS_STACK);
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
	
	public static void openDepositBox(Player player) {
		player.send(new SendString("The Bank of " + Configuration.SERVER_NAME + " - Deposit Box", 7165));
		player.send(new SendInventoryInterface(4465, 197));
	}
	
	public Player getPlayer() {
		return player;
	}

}
