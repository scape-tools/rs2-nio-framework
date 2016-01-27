package main.astraeus.game.model.entity.mobile.player.update.mask;

import main.astraeus.game.model.Animation;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.net.packet.PacketWriter;
import main.astraeus.net.protocol.codec.ByteModification;
import main.astraeus.net.protocol.codec.ByteOrder;

/**
 * The {@link PlayerUpdateBlock} implementation that updates a players animation
 * state.
 * 
 * @author SeVen
 */
public class PlayerAnimationUpdateBlock extends PlayerUpdateBlock {

	/**
	 * Creates a new {@link PlayerAnimationUpdateBlock}.
	 */
	public PlayerAnimationUpdateBlock() {
		super(0x8, UpdateFlag.ANIMATION);
	}

	@Override
	public void encode(Player entity, PacketWriter writer) {	      
		final Animation animation = entity.getAnimation();
		writer.writeShort(animation.getId(), ByteOrder.LITTLE)
		.write(animation.getDelay(), ByteModification.NEGATION);
	}

}
