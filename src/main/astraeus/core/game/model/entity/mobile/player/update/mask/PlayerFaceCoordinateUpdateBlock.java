package main.astraeus.core.game.model.entity.mobile.player.update.mask;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.Player.Attributes;
import main.astraeus.core.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.protocol.codec.ByteOrder;
import main.astraeus.core.net.protocol.codec.ByteModification;

/**
 * The {@link PlayerUpdateBlock} implementation that updates a players facing
 * direction.
 * 
 * @author SeVen
 */
public class PlayerFaceCoordinateUpdateBlock extends PlayerUpdateBlock {

	/**
	 * Creates a new {@link PlayerFaceCoordinateUpdateBlock}.
	 */
	public PlayerFaceCoordinateUpdateBlock() {
		super(0x2, UpdateFlag.FACE_COORDINATE);
	}

	@Override
	public void encode(Player entity, PacketBuilder builder) {
		builder.putShort((Integer) entity.getAttributes().get(Attributes.FACING_COORDINATE_X), ByteModification.ADDITION,
				ByteOrder.LITTLE)
				.putShort((Integer) entity.getAttributes().get(Attributes.FACING_COORDINATE_Y), ByteOrder.LITTLE);
	}

}
