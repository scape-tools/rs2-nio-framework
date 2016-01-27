package main.astraeus.game.model.entity.mobile.player.update.mask;

import main.astraeus.game.model.ForceMovement;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.net.packet.PacketWriter;
import main.astraeus.net.protocol.codec.ByteModification;

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
	public void encode(Player entity, PacketWriter writer) {	      

		ForceMovement movement = entity.getForceMovement();

		writer.write(movement.getStartLocation().getX(), ByteModification.ADDITION)
				.write(movement.getStartLocation().getY(), ByteModification.NEGATION)
				.write(movement.getEndLocation().getX(), ByteModification.SUBTRACTION).write(movement.getEndLocation().getY())
				.writeShort(movement.getDurationX()).write(movement.getDurationY(), ByteModification.ADDITION)
				.write(movement.getDirection().getId());
	}

}
