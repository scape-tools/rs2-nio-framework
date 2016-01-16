package main.astraeus.core.game.model.entity.mobile.player.update.mask;

import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.packet.PacketBuilder;

/**
 * The {@link PlayerUpdateBlock implementation that displays a single hit-mark
 * on a player.
 * 
 * @author SeVen
 */
public class PlayerSingleHitUpdateBlock extends PlayerUpdateBlock {

	/**
	 * Creates a new {@link PlayerSingleHitUpdateBlock}.
	 */
	public PlayerSingleHitUpdateBlock() {
		super(0x20, UpdateFlag.SINGLE_HIT);
	}

	@Override
	public void encode(Player entity, PacketBuilder builder) {
//		builder.put(entity.getPrimaryHit().getDamage())
//				.put(entity.getPrimaryHit().getType().getId(), ByteValue.ADDITION)
//				.put(entity.getSkill().getLevel(Skill.HITPOINTS), ByteValue.NEGATION)
//				.put(entity.getSkill().getMaxLevel(Skill.HITPOINTS));
	}

}
