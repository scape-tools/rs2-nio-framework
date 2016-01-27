package main.astraeus.core.game.model.entity.mobile.player.update.mask;

import main.astraeus.core.game.model.ChatMessage;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.protocol.codec.ByteOrder;
import main.astraeus.core.net.protocol.codec.ByteModification;

/**
 * The {@link PlayerUpdateBlock} implementation that updates a players chat
 * text.
 * 
 * @author SeVen
 */
public class PlayerChatUpdateBlock extends PlayerUpdateBlock {

	/**
	 * Creates a new {@link PlayerChatUpdateBlock}.
	 */
	public PlayerChatUpdateBlock() {
		super(0x80, UpdateFlag.CHAT);
	}

	@Override
	public void encode(Player entity, PacketBuilder builder) {
		final ChatMessage msg = entity.getChatMessage();
		final byte[] bytes = msg.getText();

		builder.putShort(((msg.getColor() & 0xFF) << 8) + (msg.getEffect() & 0xFF), ByteOrder.LITTLE)
				.put(entity.getRights().getProtocolValue()).put(bytes.length, ByteModification.NEGATION)
				.putBytesReverse(bytes);
	}

}
