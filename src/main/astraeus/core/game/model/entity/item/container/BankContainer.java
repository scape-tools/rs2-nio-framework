package main.astraeus.core.game.model.entity.item.container;

import main.astraeus.core.game.model.entity.item.Item;
import main.astraeus.core.game.model.entity.item.ItemContainer;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.Player.Attributes;
import main.astraeus.core.net.channel.packet.outgoing.SendInventoryInterface;

public class BankContainer extends ItemContainer {

	private final Player player;
	
	public BankContainer(Player player) {
		super(352, StackType.ALWAYS_STACK);
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
	
	public static void openBank(Player player) {
		player.getAttributes().put(Attributes.BANKING, true);
		player.send(new SendInventoryInterface(5292, 5063));
		//player.sendItemOnInterface(5064, new Item[]{new Item(4151, 1), new Item(995, 1000000)});
		//player.sendItemOnInterface(5382, new Item[]{new Item(6585, 4)});
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
