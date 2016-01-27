package main.astraeus.core.game.model.entity.mobile.player.update.mask;

import main.astraeus.core.game.model.ForceMovement;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.protocol.codec.ByteModification;

/**
 * The {@link PlayerUpdateBlock} implementation that updates a players forced
 * movement.
 * 
 * @author SeVen
 */
public class PlayerForceMovementUpdateBlock extends PlayerUpdateBlock {

	/**
	 * Creates a new {@link PlayerForcedMovementUpdateBlock}.
	 */
	public PlayerForceMovementUpdateBlock() {
		super(0x400, UpdateFlag.FORCE_MOVEMENT);
	}

	@Override
	public void encode(Player entity, PacketBuilder builder) {

		ForceMovement movement = entity.getForceMovement();

		builder.put(movement.getStartLocation().getX(), ByteModification.ADDITION)
				.put(movement.getStartLocation().getY(), ByteModification.NEGATION)
				.put(movement.getEndLocation().getX(), ByteModification.SUBTRACTION).put(movement.getEndLocation().getY())
				.putShort(movement.getDurationX()).put(movement.getDurationY(), ByteModification.ADDITION)
				.put(movement.getDirection().getId());
	}

}
