package com.runescape.core.game.model.entity.character.player;

import java.io.IOException;

import com.runescape.core.game.model.entity.EntityEventListener;
import com.runescape.core.game.model.entity.character.MobileEntity;
import com.runescape.core.game.model.entity.character.player.appearance.Appearance;
import com.runescape.core.game.model.entity.character.player.update.UpdateBlock;
import com.runescape.core.game.utility.cryption.CryptionAlgorithmPair;
import com.runescape.core.net.channel.PlayerIO;
import com.runescape.core.net.channel.events.WriteChannelEvent;
import com.runescape.core.net.channel.message.PacketBuilder;
import com.runescape.core.net.channel.message.outgoing.PacketSender;

public final class Player extends MobileEntity {

	/**
	 * The context of this player's channel.
	 */
	private final PlayerIO context;	

	/**
	 * The pair of cryptography algorithms for encoding and decoding.
	 */
	private CryptionAlgorithmPair cryptographyPair;

	/**
	 * The details of this player's account.
	 */
	private final PlayerDetails details = new PlayerDetails(this);

	/**
	 * The visual appearance of the player in the virtual world.
	 */
	private final Appearance appearance = new Appearance(this);
	
	private PacketSender packetSender = new PacketSender(this);

	/**
	 * The overloaded class constructor used for instantiation of this
	 * class file.
	 * 
	 * @param context The context of this player's channel.
	 */
	public Player(PlayerIO context) {
		this.context = context;
	}

	/**
	 * Writes a single {@link OutgoingPacket} to the game client.
	 * 
	 * @param packet A new instance of the {@link OutgoingPacket} being dispensed.
	 * 
	 * @throws IOException The exception thrown if an error occurs while dispensing
	 * the packet.
	 */
	public final void write(PacketBuilder builder) {
		getContext().execute(new WriteChannelEvent(builder.getHeader(), builder));
	}
	
	/**
	 * Appends an individual piece of the main updating block.
	 * 
	 * @param block The piece of the main updating block.
	 * 
	 * @param buffer The internal buffer.
	 * 
	 * @param player The player being updated.
	 */
	public final void append(UpdateBlock block, PacketBuilder buffer, Player player) {
		block.update(this, buffer);
	}

	/**
	 * Returns an instance of this player's channel's context.
	 * 
	 * @return The returned instance.
	 */
	public PlayerIO getContext() {
		return context;
	}

	/**
	 * Returns an instance of the pair of cryptography algorithms for encoding 
	 * and decoding.
	 *
	 * @return The returned instance.
	 */
	public CryptionAlgorithmPair getCryptographyPair() {
		return cryptographyPair;
	}

	/**
	 * Modifies the instance of the pair of cryptography algorithms for 
	 * encoding and decoding.
	 *
	 * @param cryptographyPair The new modification.
	 */
	public void setCryptographyPair(CryptionAlgorithmPair cryptographyPair) {
		this.cryptographyPair = cryptographyPair;
	}

	/**
	 * Returns an instance of the player's account details.
	 * 
	 * @return The returned instance.
	 */
	public PlayerDetails getDetails() {
		return details;
	}

	/**
	 * Returns an instance of the player's appearance.
	 * 
	 * @return The returned instance.
	 */
	public Appearance getAppearance() {
		return appearance;
	}

	@Override
	public EntityEventListener<Player> getEventListener() {
		return new PlayerEventListener();
	}

	@Override
	public String toString() {
		return details.getUsername() + " " + details.getPassword() + " " + details.getAddress();
	}
	
	public PacketSender getPacketSender() {
		return packetSender;
	}
	
}