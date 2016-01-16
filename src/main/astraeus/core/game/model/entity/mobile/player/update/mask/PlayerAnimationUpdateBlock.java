package main.astraeus.core.game.model.entity.mobile.player.update.mask;

import main.astraeus.core.game.model.Animation;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.protocol.codec.ByteOrder;
import main.astraeus.core.net.protocol.codec.ByteValue;

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
	public void encode(Player entity, PacketBuilder builder) {
		final Animation animation = entity.getAnimation();
		builder.putShort(animation.getId(), ByteOrder.LITTLE).put(animation.getDelay(), ByteValue.NEGATION);
	}

}
