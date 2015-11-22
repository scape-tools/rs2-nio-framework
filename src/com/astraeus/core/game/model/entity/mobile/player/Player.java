package com.astraeus.core.game.model.entity.mobile.player;

import java.util.EnumMap;

import com.astraeus.core.game.content.dialogue.Dialogue;
import com.astraeus.core.game.content.dialogue.DialogueOption;
import com.astraeus.core.game.model.entity.EntityEventListener;
import com.astraeus.core.game.model.entity.item.ItemContainer;
import com.astraeus.core.game.model.entity.item.container.InventoryContainer;
import com.astraeus.core.game.model.entity.mobile.MobileEntity;
import com.astraeus.core.game.model.entity.mobile.player.appearance.Appearance;
import com.astraeus.core.game.model.entity.mobile.player.event.file.PlayerReadFileEvent;
import com.astraeus.core.game.model.entity.mobile.player.event.file.PlayerSaveFileEvent;
import com.astraeus.core.net.channel.PlayerChannel;
import com.astraeus.core.net.channel.events.WriteChannelEvent;
import com.astraeus.core.net.channel.packet.OutgoingPacket;
import com.astraeus.core.net.channel.packet.outgoing.PacketSender;
import com.astraeus.core.net.channel.packet.outgoing.impl.ChatBoxMessagePacket;
import com.astraeus.core.net.security.IsaacRandomPair;
import com.astraeus.core.utility.Decodeable;
import com.astraeus.core.utility.Utilities;
import com.astraeus.core.utility.Encodeable;

public final class Player extends MobileEntity {

	/**
	 * The context of this player's channel.
	 */
	private final PlayerChannel context;	

	/**
	 * The pair of cryptography algorithms for encoding and decoding.
	 */
	private IsaacRandomPair isaacRandomPair;
	
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
	 * Displays server debug messages to admins and developers.
	 */
	private boolean serverDebug = false;

	/**
	 * The overloaded class constructor used for instantiation of this
	 * class file.
	 * 
	 * @param context The context of this player's channel.
	 */
	public Player(PlayerChannel context) {
		this.context = context;
	}
	
	/**
	 * Sends a message into this players chatbox.
	 */
	public void sendMessage(String message) {
		write(new ChatBoxMessagePacket(message));
	}
	
	/**
	 * Gets this players username.
	 */
	public String getUsername() {
		return this.getDetails().getUsername();
	}
	
	/**
	 * Gets this players rights.
	 */
	public Rights getRights() {
		return this.getDetails().getRights();
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
	 */
	public final void write(OutgoingPacket packet) {
		getContext().execute(new WriteChannelEvent(packet.getHeader(), packet.dispatch(this)));
	}

	/**
	 * Executes a writable event.
	 * 
	 * @param event
	 * 		The writable event to execute.
	 * 
	 * {@code true} If this operation can be performed, {@code false} otherwise.
	 */
	public final boolean executeWritableEvent(Encodeable event) {
		return event.serialize();
	}
	
	/**
	 * Executes a readable event.
	 * 
	 * @param event
	 * 		The readable event to execute.
	 * 
	 * {@code true} If this operation can be performed, {@code false} otherwise.
	 */
	public final boolean executeReadableEvent(Decodeable event) {
		return event.deserialize();
	}
	
	/**
	 * Executes a save operation for a player.
	 * 
	 * {@code true} If this operation can be performed, {@code false} otherwise.
	 */
	public final boolean save() {
		return executeWritableEvent(new PlayerSaveFileEvent(this));
	}
	
	/**
	 * Executes a read operation over a players file.
	 * 
	 * {@code true} If this operation can be performed, {@code false} otherwise.
	 */
	public final boolean load() {
		return executeReadableEvent(new PlayerReadFileEvent(this));
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
	public PlayerChannel getContext() {
		return context;
	}

	/**
	 * Returns an instance of the pair of cryptography algorithms for encoding 
	 * and decoding.
	 *
	 * @return The returned instance.
	 */
	public IsaacRandomPair getIsaacRandomPair() {
		return isaacRandomPair;
	}

	/**
	 * Modifies the instance of the pair of cryptography algorithms for 
	 * encoding and decoding.
	 *
	 * @param isaacRandomPair The new modification.
	 */
	public void setCryptographyPair(IsaacRandomPair isaacRandomPair) {
		this.isaacRandomPair = isaacRandomPair;
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
	
	public PacketSender getPacketSender() {
		return packetSender;
	}
	
	/**
	 * @return the serverDebug
	 */
	public boolean isServerDebug() {
		return serverDebug;
	}
	
	/**
	 * Sets the server in debug mode for this player.
	 * 
	 * @param serverDebug
	 * 
	 * {@code true} debug mode on, {@code false} otherwise.
	 */
	public void setServerDebug(boolean serverDebug) {
		this.serverDebug = serverDebug;
	}
	
	@Override
	public EntityEventListener<Player> getEventListener() {
		return new PlayerEventListener();
	}

	@Override
	public String toString() {
		return Utilities.capitalizePlayerName(details.getUsername()) + " " + details.getAddress();
	}
	
}