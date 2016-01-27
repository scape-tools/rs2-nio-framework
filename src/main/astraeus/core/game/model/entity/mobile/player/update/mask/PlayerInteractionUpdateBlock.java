package main.astraeus.core.game.model.entity.mobile.player.update.mask;

import main.astraeus.core.game.model.entity.Entity;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.packet.PacketWriter;
import main.astraeus.core.net.protocol.codec.ByteOrder;

/**
 * The {@link PlayerUpdateBlock implementation that updates a players
 * interaction.
 * 
 * @author SeVen
 */
public class PlayerInteractionUpdateBlock extends PlayerUpdateBlock {

    /**
     * Creates a new {@link PlayerInteractionUpdateBlock}.
     */
    public PlayerInteractionUpdateBlock() {
	super(0x1, UpdateFlag.ENTITY_INTERACTION);
    }

    @Override
    public void encode(Player target, PacketWriter builder) {

	final Entity entity = target.getInteractingEntity();

	if (entity != null) {
	    int index = entity.getSlot();

	    if (entity instanceof Player) {
		index += +32768;
	    }

	    builder.writeShort(index, ByteOrder.LITTLE);
	} else {
	    builder.writeShort(-1, ByteOrder.LITTLE);
	}

    }

}
