package com.astraeus.core.net.channel.packet.outgoing;

import java.util.Iterator;

import com.astraeus.core.Server;
import com.astraeus.core.game.GameConstants;
import com.astraeus.core.game.model.entity.Position;
import com.astraeus.core.game.model.entity.UpdateFlags;
import com.astraeus.core.game.model.entity.item.Item;
import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.model.entity.mobile.player.update.impl.PlayerMovementBlock;
import com.astraeus.core.game.model.entity.mobile.player.update.impl.RegionalMovementBlock;
import com.astraeus.core.game.model.entity.mobile.player.update.impl.StatefulUpdateBlock;
import com.astraeus.core.game.model.entity.object.GameObject;
import com.astraeus.core.net.channel.packet.PacketBuilder;
import com.astraeus.core.net.channel.packet.PacketHeader;
import com.astraeus.core.net.channel.protocol.codec.game.ByteAccess;
import com.astraeus.core.net.channel.protocol.codec.game.ByteOrder;
import com.astraeus.core.net.channel.protocol.codec.game.ByteValue;

/**
 * Contains outgoing packets that can be sent to the Client.
 * 
 * @author SeVen
 */
public class PacketSender {

	/**
	 * The player that is sending the packet.
	 */
	private Player player;

	/**
	 * Constructs a new {@link PacketSender} which sends
	 * a packet to the client.
	 * 
	 * @param player
	 * 		The player that is sending the packet.
	 */
	public PacketSender(Player player) {
		this.player = player;
	}

	/**
	 * Sends a message in the chatbox.
	 * 
	 * @param message
	 * 		The message to display in the chatbox.
	 * 
	 * @param The instance of this encoder.
	 */
	public PacketSender sendMessage(String message) {
		PacketBuilder out = new PacketBuilder(253, PacketHeader.VARIABLE_BYTE);
		out.allocate(message.length() + 3);
		player.getContext().prepare(out);
		out.getInternal().put(message.getBytes());
		out.putByte(10);
		player.write(out);
		return this;
	}
	
	public PacketSender sendCreateObject(GameObject object) {
		PacketBuilder out = new PacketBuilder(151);
		out.allocate(5);
		player.getContext().prepare(out);
		out.put(0, ByteValue.SUBTRACTION);
		out.putShort(object.getId(), ByteOrder.LITTLE);
		out.putByte((object.getType() << 2) + (object.getFacing().getDirection() & 3), ByteValue.SUBTRACTION);
		player.write(out);
		return this;
	}
	
	public PacketSender sendString(String string, int widget) {
		PacketBuilder out = new PacketBuilder(126, PacketHeader.VARIABLE_SHORT);
		out.allocate(string.length() + 6);
		player.getContext().prepare(out);
		out.putString(string);
		out.putShort(widget, ByteValue.ADDITION);
		out.endVariableShortPacketHeader();	
		player.write(out);
		return this;
	}
	
    /**
     * The outgoing packet that opens an interface and displays another interface over
     * the inventory area.
     *
     * @param open
     *            the interface to open.
     * @param overlay
     *            the interface to send on the inventory area.
     * @return an instance of this encoder.
     */
	public PacketSender sendInventoryInterface(int open, int overlay) {
		PacketBuilder out = new PacketBuilder(248);
		out.allocate(5);
		player.getContext().prepare(out);
		out.putShort(open, ByteValue.ADDITION);
		out.putShort(overlay);
		player.write(out);
		return this;
	}
	
	public PacketSender sendItemsOnInterface(int interfaceId, Item[] items) {
		PacketBuilder out = new PacketBuilder(53, PacketHeader.VARIABLE_SHORT);
		out.allocate(2048);
		player.getContext().prepare(out);
		out.putShort(interfaceId);
		out.putShort(items.length);
		for(Item item : items) {
			if (item != null) {
			if(item.getAmount() > 254) {
				out.putByte(255);
				out.putInt(item.getAmount(), ByteOrder.INVERSE);				
			} else {
				out.putByte(item.getAmount());
			}
			out.putShort(item.getId() + 1, ByteValue.ADDITION, ByteOrder.LITTLE);
			} else {
				out.putByte(0);
				out.putShort(0, ByteValue.ADDITION, ByteOrder.LITTLE);
			}			
		}
		out.endVariableShortPacketHeader();
		player.write(out);
		return this;
	}
	
	public PacketSender sendChatInterface(int interfaceId) {
		PacketBuilder out = new PacketBuilder(164);
		out.allocate(3);
		player.getContext().prepare(out);
		out.putShort(interfaceId, ByteOrder.LITTLE);
		player.write(out);
		return this;
	}
	
	public PacketSender sendDialogueAnimation(int interfaceId, int animationId) {		
		PacketBuilder out = new PacketBuilder(200);		
		out.allocate(10);
		player.getContext().prepare(out);
		out.putShort(interfaceId);
		out.putShort(animationId);
		player.write(out);		
		return this;
	}
	
	public PacketSender sendDialogueNpcHead(int npcId, int interfaceId) {
		PacketBuilder out = new PacketBuilder(75);
		out.allocate(15);
		player.getContext().prepare(out);
		out.putShort(npcId, ByteValue.ADDITION, ByteOrder.LITTLE);
		out.putShort(interfaceId, ByteValue.ADDITION, ByteOrder.LITTLE);
		player.write(out);
		return this;
	}
	
	public PacketSender sendDialoguePlayerHead(int interfaceId) {
		PacketBuilder out = new PacketBuilder(185);
		out.allocate(10);
		player.getContext().prepare(out);
		out.putShort(interfaceId, ByteValue.ADDITION, ByteOrder.LITTLE);
		player.write(out);		
		return this;
	}
	
	public PacketSender sendCreateCoordinate(Position position) {
		PacketBuilder out = new PacketBuilder(85);
		out.allocate(3);
		player.getContext().prepare(out);
		out.putByte(position.getY() -  8 * player.getLastPosition().getRegionalY() , ByteValue.INVERSION);
		out.putByte(position.getX() -  8 * player.getLastPosition().getRegionalX() , ByteValue.INVERSION);
		player.write(out);
		return this;
	}
	
	/**
	 * The outgoing packet that closes a players opened interface.
	 */
	public PacketSender sendCloseInterface() {
		PacketBuilder out = new PacketBuilder(219);
		out.allocate(1);
		player.getContext().prepare(out);
		player.write(out);		
		return this;
	}
	
	/**
	 * The outgoing packet that logs a player out of the game.
	 */
	public PacketSender sendLogout() {
		PacketBuilder out = new PacketBuilder(109);
		out.allocate(1);
		player.getContext().prepare(out);
		player.write(out);
		return this;
	}

	/**
	 * Updates a single player.
	 * 
	 * The player updating process consists of 4 parts.
	 * 
	 * 1. Our player movement updates
	 * 2. Other player movement updates
	 * 3. Player list updating
	 * 		a) Appearance updating
	 * 		b) Location updating
	 * 4. Player update block flag-based updates. 
	 * 
	 * @param The instance of this encoder.
	 */
	public PacketSender sendPlayerUpdate() {		
		if(player.getUpdateFlags().contains(UpdateFlags.UPDATE_MAP_REGION)) {
			player.getPacketSender().sendRegionalUpdate();
		}
		
		PacketBuilder update = new PacketBuilder();

		update.allocate(8192);
		
		PacketBuilder out = new PacketBuilder(81, PacketHeader.VARIABLE_SHORT);
		
		out.allocate(16384);
		
		player.getContext().prepare(out);
		out.setAccessType(ByteAccess.BIT_ACCESS);

		player.append(new PlayerMovementBlock(), out, player);

		if (player.updateRequired()) {
			player.append(new StatefulUpdateBlock(false), update, player);
		}
		out.putBits(8, player.getLocalPlayers().size());

		for (Iterator<Player> iterator = player.getLocalPlayers().iterator(); iterator
				.hasNext();) {
			final Player other = iterator.next();

			if (other.getPosition().isWithinDistance(player.getPosition(), 15)) {
				player.append(new RegionalMovementBlock(), out, other);

				if (other.updateRequired()) {
					player.append(new StatefulUpdateBlock(false), update, other);
				}

			} else {
				iterator.remove();
				out.putBits(1, 1);
				out.putBits(2, 3);
			}
		}

		for (Player other : Server.getUpdateProcessor().getPlayers().values()) {
			if (other.getLocalPlayers().size() >= 255) {
				break;
			}

			if (other == player || player.getLocalPlayers().contains(other)) {
				continue;

			}
			if (other.getPosition().isWithinDistance(player.getPosition(), 15)) {
				player.getLocalPlayers().add(other);
				out.putBits(11, other.getIndex());
				out.putBits(1, 1);
				out.putBits(1, 1);
				out.putBits(5, other.getPosition().getY()
						- player.getPosition().getY());
				out.putBits(5, other.getPosition().getX()
						- player.getPosition().getX());
				player.append(new StatefulUpdateBlock(true), update, other);
			}
		}

		if (update.getInternal().position() > 0) {
			out.putBits(11, 2047);
			out.setAccessType(ByteAccess.BYTE_ACCESS);
			out.putBytes(update.getInternal());

		} else {
			out.setAccessType(ByteAccess.BYTE_ACCESS);
		}
		player.write(out);
		return this;
	}

	/**
	 * The packet that sends the current map region.
	 * 
	 * @param The instance of this encoder.
	 */
	public PacketSender sendRegionalUpdate() {
		PacketBuilder out = new PacketBuilder(73, PacketHeader.STANDARD);
		out.allocate(5);
		player.getContext().prepare(out);
		out.putShort(player.getPosition().getRegionalX() + 6,
				ByteValue.ADDITION, ByteOrder.BIG);
		out.putShort(player.getPosition().getRegionalY() + 6);
		player.setLastPosition(player.getPosition());
		player.write(out);
		return this;
	}

	/**
	 * Sends an interface as a tabbed interface.
	 * 
	 * @param tabId
	 * 		The id of the tab see {@link GameConstants}
	 * 
	 * @param interfaceId
	 * 		The interface to show.
	 * 
	 * @param The instance of this encoder.
	 */
	public PacketSender sendTabInterface(int tabId, int interfaceId) {
		PacketBuilder out = new PacketBuilder(71);
		out.allocate(4);
		player.getContext().prepare(out);	
		out.putShort(interfaceId);
		out.put(tabId, ByteValue.ADDITION);
		player.write(out);
		return this;
	}

	/**
	 * Creates all the in-game tabs for a player.
	 * 
	 * @param The instance of this encoder.
	 */
	public PacketSender sendTabs() {
		sendTabInterface(GameConstants.ATTACK_TAB, 2423);
		sendTabInterface(GameConstants.SKILLS_TAB, 3917);
		sendTabInterface(GameConstants.QUESTS_TAB, 638);
		sendTabInterface(GameConstants.INVENTORY_TAB, 3213);
		sendTabInterface(GameConstants.EQUIPMENT_TAB, 1644);
		sendTabInterface(GameConstants.MAGIC_TAB, 1151); // 12855 ancient
		sendTabInterface(GameConstants.PRAYER_TAB, 5608);

		sendTabInterface(GameConstants.FRIEND_TAB, 5065);
		sendTabInterface(GameConstants.IGNORE_TAB, 5715);
		sendTabInterface(GameConstants.LOGOUT, 2449);
		sendTabInterface(GameConstants.OPTIONS_TAB, 904);
		sendTabInterface(GameConstants.EMOTES_TAB, 147);
		sendTabInterface(GameConstants.MUSIC_TAB, 962);
		return this;
	}

}
