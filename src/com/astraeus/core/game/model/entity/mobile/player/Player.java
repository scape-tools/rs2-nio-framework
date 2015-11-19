package com.astraeus.core.game.model.entity.mobile.player;

import java.io.IOException;
import java.util.EnumMap;

import com.astraeus.core.game.content.dialogue.Dialogue;
import com.astraeus.core.game.content.dialogue.DialogueOption;
import com.astraeus.core.game.model.entity.EntityEventListener;
import com.astraeus.core.game.model.entity.item.ItemContainer;
import com.astraeus.core.game.model.entity.item.container.InventoryContainer;
import com.astraeus.core.game.model.entity.mobile.MobileEntity;
import com.astraeus.core.game.model.entity.mobile.player.appearance.Appearance;
import com.astraeus.core.game.model.entity.mobile.player.update.UpdateBlock;
import com.astraeus.core.net.channel.PlayerIO;
import com.astraeus.core.net.channel.events.WriteChannelEvent;
import com.astraeus.core.net.channel.message.PacketBuilder;
import com.astraeus.core.net.channel.message.outgoing.PacketSender;
import com.astraeus.core.net.security.IsaacRandomPair;

public final class Player extends MobileEntity {

	/**
	 * The context of this player's channel.
	 */
	private final PlayerIO context;	

	/**
	 * The pair of cryptography algorithms for encoding and decoding.
	 */
	private IsaacRandomPair cryptographyPair;
	
	/**
	 * The current dialogue state.
	 */
	private Dialogue dialogue;

	/**
	 * The listener for dialogue option selection.
	 */
	private DialogueOption dialogueOption;
	
	/**
	 * The inventory container for this player.
	 */
	private final ItemContainer inventoryContainer = new InventoryContainer(this);

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
	 * The attributes a player can have.
	 */
	public enum Attributes {
		USERNAME,
		PASSWORD,
		SHOPPING,
		TRADING,
		BANKING,
		CLICK_X,
		CLICK_Y,
		WALK_TO_ACTION,
		CLICK_INDEX,
		FACE_DIRECTION;
	}

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
	 * Prepares a prior update method to the main update block.
	 */
	public final void prepare() {
		getMovement().handleEntityMovement();
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
	 * Returns an encapsulated instance of the dialogue state.
	 * 
	 * @return The returned instance.
	 */
	public Dialogue getDialogue() {
		return dialogue;
	}

	/**
	 * Modifies the current instance of the dialogue state.
	 * 
	 * @param dialogue The new modification.
	 */
	public void setDialogue(Dialogue dialogue) {
		this.dialogue = dialogue;
	}

	public DialogueOption getDialogueOption() {
		return dialogueOption;
	}

	public void setDialogueOption(DialogueOption dialogueOption) {
		this.dialogueOption = dialogueOption;
	}
	
	/**
	 * @return the attributes
	 */
	public EnumMap<Attributes, Object> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttribute(EnumMap<Attributes, Object> attributes) {
		this.attributes = attributes;
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
	public IsaacRandomPair getCryptographyPair() {
		return cryptographyPair;
	}

	/**
	 * Modifies the instance of the pair of cryptography algorithms for 
	 * encoding and decoding.
	 *
	 * @param cryptographyPair The new modification.
	 */
	public void setCryptographyPair(IsaacRandomPair cryptographyPair) {
		this.cryptographyPair = cryptographyPair;
	}
	
	/**
	 * @return the inventoryContainer
	 */
	public ItemContainer getInventoryContainer() {
		return inventoryContainer;
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