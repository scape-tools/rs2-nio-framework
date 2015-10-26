package com.runescape.core.net.channel.message.outgoing;

import java.util.Iterator;

import com.runescape.core.Server;
import com.runescape.core.game.GameConstants;
import com.runescape.core.game.model.entity.character.player.Player;
import com.runescape.core.game.model.entity.character.player.update.impl.PlayerMovementBlock;
import com.runescape.core.game.model.entity.character.player.update.impl.RegionalMovementBlock;
import com.runescape.core.game.model.entity.character.player.update.impl.StatefulUpdateBlock;
import com.runescape.core.net.ByteAccess;
import com.runescape.core.net.ByteOrder;
import com.runescape.core.net.ByteValue;
import com.runescape.core.net.channel.message.PacketBuilder;
import com.runescape.core.net.channel.message.Packet.PacketHeader;

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
	 */
	public PacketSender sendMessage(String message) {
		PacketBuilder out = new PacketBuilder(253, PacketHeader.VARIABLE_BYTE);
		out.allocate(message.length() + 3);
		player.getContext().prepare(out);
		out.getInternal().put(message.getBytes());
		out.putByte(10, ByteValue.STANDARD);
		player.write(out);
		return this;
	}

	public PacketSender sendPlayerUpdate() {
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

			if (other.getLocation().isWithinDistance(player.getLocation(), 15)) {
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
			if (other.getLocation().isWithinDistance(player.getLocation(), 15)) {
				player.getLocalPlayers().add(other);
				out.putBits(11, other.getIndex());
				out.putBits(1, 1);
				out.putBits(1, 1);
				out.putBits(5, other.getLocation().getY()
						- player.getLocation().getY());
				out.putBits(5, other.getLocation().getX()
						- player.getLocation().getX());
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
	 */
	public PacketSender sendRegionalUpdate() {
		PacketBuilder out = new PacketBuilder(73, PacketHeader.STANDARD);
		out.allocate(5);
		player.getContext().prepare(out);
		out.putShort(player.getLocation().getRegionalX() + 6,
				ByteValue.ADDITIONAL, ByteOrder.BIG_BYTE_ORDER);
		out.putShort(player.getLocation().getRegionalY() + 6,
				ByteValue.STANDARD);
		player.setLastLocation(player.getLocation());
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
	 */
	public PacketSender sendTabInterface(int tabId, int interfaceId) {
		PacketBuilder out = new PacketBuilder(71);
		out.allocate(4);
		player.getContext().prepare(out);	
		out.putShort(interfaceId);
		out.put(tabId, ByteValue.ADDITIONAL);
		player.write(out);
		return this;
	}

	/**
	 * Creates all the in-game tabs for a player.
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
