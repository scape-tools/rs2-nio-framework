package com.astraeus.core.game.model.entity.mobile.player;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

import com.astraeus.core.game.GameConstants;
import com.astraeus.core.game.content.dialogue.Dialogue;
import com.astraeus.core.game.content.dialogue.DialogueOption;
import com.astraeus.core.game.model.entity.EntityEventListener;
import com.astraeus.core.game.model.entity.item.Item;
import com.astraeus.core.game.model.entity.item.ItemContainer;
import com.astraeus.core.game.model.entity.item.container.InventoryContainer;
import com.astraeus.core.game.model.entity.mobile.MobileEntity;
import com.astraeus.core.game.model.entity.mobile.npc.Npc;
import com.astraeus.core.game.model.entity.mobile.player.appearance.Appearance;
import com.astraeus.core.game.model.entity.mobile.player.event.file.PlayerReadFileEvent;
import com.astraeus.core.game.model.entity.mobile.player.event.file.PlayerSaveFileEvent;
import com.astraeus.core.net.channel.PlayerChannel;
import com.astraeus.core.net.channel.events.WriteChannelEvent;
import com.astraeus.core.net.channel.packet.OutgoingPacket;
import com.astraeus.core.net.channel.packet.outgoing.ChatBoxMessagePacket;
import com.astraeus.core.net.channel.packet.outgoing.ChatInterfacePacket;
import com.astraeus.core.net.channel.packet.outgoing.ClearScreenPacket;
import com.astraeus.core.net.channel.packet.outgoing.DisplayInterfacePacket;
import com.astraeus.core.net.channel.packet.outgoing.InterfaceAnimationPacket;
import com.astraeus.core.net.channel.packet.outgoing.InventoryInterfacePacket;
import com.astraeus.core.net.channel.packet.outgoing.ItemOnInterfacePacket;
import com.astraeus.core.net.channel.packet.outgoing.LogoutPacket;
import com.astraeus.core.net.channel.packet.outgoing.NpcDialogueHeadPacket;
import com.astraeus.core.net.channel.packet.outgoing.PlayerDialogueHeadPacket;
import com.astraeus.core.net.channel.packet.outgoing.RegionalUpdatePacket;
import com.astraeus.core.net.channel.packet.outgoing.SendStringPacket;
import com.astraeus.core.net.channel.packet.outgoing.SideBarInterfacePacket;
import com.astraeus.core.net.security.IsaacRandomPair;
import com.astraeus.core.utility.Decodeable;
import com.astraeus.core.utility.Utilities;
import com.astraeus.core.utility.Encodeable;

public final class Player extends MobileEntity {
	
	/**
	 * The mobs local to our player.
	 */
	private final List<Npc> localNpcs = new LinkedList<Npc>();

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
		FACE_COORDINATE;
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
	
	public boolean isBusy() {
		if(((Boolean) getAttributes().get(Attributes.BANKING))
				|| (Boolean) getAttributes().get(Attributes.SHOPPING)
				|| (Boolean) getAttributes().get(Attributes.TRADING)) {
			return true;
		}
		return false;
	}
	
	public void sendDisplayInterface(int interfaceId) {
		write(new DisplayInterfacePacket(interfaceId));
	}
	
	public void sendItemOnInterface(int interfaceId, Item[] items) {
		write(new ItemOnInterfacePacket(interfaceId, items));
	}
	
	public void sendInventoryInterface(int open, int overlay) {
		write(new InventoryInterfacePacket(open, overlay));
	}
	
	public void sendString(String string, int widget) {
		write(new SendStringPacket(string, widget));
	}
	
	public void sendClearScreen() {
		write(new ClearScreenPacket());
	}
	
	public void sendChatBoxInterface(int interfaceId) {
		write(new ChatInterfacePacket(interfaceId));
	}
	
	public void sendInterfaceAnimation(int interfaceId, int animationId) {
		write(new InterfaceAnimationPacket(interfaceId, animationId));
	}
	
	public void sendDialogueNpcHead(int npcId, int interfaceId) {
		write(new NpcDialogueHeadPacket(npcId, interfaceId));
	}
	
	public void sendDialoguePlayerHead(int interfaceId) {
		write(new PlayerDialogueHeadPacket(interfaceId));
	}
	
	public void sendRegionalUpdate() {
		write(new RegionalUpdatePacket());
	}

	/**
	 * Creates all the in-game tabs for a player.
	 * 
	 * @param The instance of this encoder.
	 */
	public void sendTabs() {		
		for(int index = 0; index < GameConstants.SIDE_BARS.length; index++) {
			write(new SideBarInterfacePacket(index, GameConstants.SIDE_BARS[index]));
		}
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
	 * Returns the collection of local mobs.
	 * 
	 * @return The returned collection.
	 */
	public final List<Npc> getLocalNpcs() {
		return localNpcs;
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

	@Override
	public void dispose() {
		write(new LogoutPacket());
	}
	
}