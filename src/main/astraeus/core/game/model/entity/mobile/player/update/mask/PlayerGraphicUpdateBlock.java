package main.astraeus.core.game.model.entity.mobile.player.update.mask;

import main.astraeus.core.game.model.Graphic;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.packet.PacketWriter;
import main.astraeus.core.net.protocol.codec.ByteOrder;

/**
 * The {@link PlayerUpdateBlock} implementation that updates a players graphics.
 * 
 * @author SeVen
 */
public class PlayerGraphicUpdateBlock extends PlayerUpdateBlock {

	/**
	 * Creates a new {@link PlayerGraphicUpdateBlock}.
	 */
	public PlayerGraphicUpdateBlock() {
		super(0x100, UpdateFlag.GRAPHICS);
	}

	@Override
	public void encode(Player entity, PacketWriter builder) {
		final Graphic graphic = entity.getGraphic();
		builder.writeShort(graphic.getId(), ByteOrder.LITTLE);
		builder.writeInt(graphic.getDelay() | graphic.getHeight());
	}

}
