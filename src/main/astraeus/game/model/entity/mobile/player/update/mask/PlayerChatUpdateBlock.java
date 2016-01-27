package main.astraeus.game.model.entity.mobile.player.update.mask;

import main.astraeus.game.model.ChatMessage;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.net.packet.PacketWriter;
import main.astraeus.net.protocol.codec.ByteModification;
import main.astraeus.net.protocol.codec.ByteOrder;

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
	public void encode(Player entity, PacketWriter writer) {	      
		final ChatMessage msg = entity.getChatMessage();
		final byte[] bytes = msg.getText();

		writer.writeShort(((msg.getColor() & 0xFF) << 8) + (msg.getEffect() & 0xFF), ByteOrder.LITTLE)
				.write(entity.getRights().getProtocolValue()).write(bytes.length, ByteModification.NEGATION)
				.writeBytesReverse(bytes);
	}

}
