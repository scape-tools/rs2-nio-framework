package main.astraeus.game.model.entity.mobile.player;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

import main.astraeus.content.dialogue.Dialogue;
import main.astraeus.content.dialogue.DialogueOption;
import main.astraeus.game.GameConstants;
import main.astraeus.game.model.ChatMessage;
import main.astraeus.game.model.entity.EntityEventListener;
import main.astraeus.game.model.entity.item.ItemContainer;
import main.astraeus.game.model.entity.item.container.InventoryContainer;
import main.astraeus.game.model.entity.mobile.MobileEntity;
import main.astraeus.game.model.entity.mobile.npc.Npc;
import main.astraeus.game.model.entity.mobile.player.appearance.Appearance;
import main.astraeus.game.model.entity.mobile.player.event.file.PlayerReadFileEvent;
import main.astraeus.game.model.entity.mobile.player.event.file.PlayerSaveFileEvent;
import main.astraeus.net.channel.PlayerChannel;
import main.astraeus.net.channel.events.WriteChannelEvent;
import main.astraeus.net.packet.outgoing.OutgoingPacket;
import main.astraeus.net.packet.outgoing.impl.SendLogout;
import main.astraeus.net.packet.outgoing.impl.SendMessage;
import main.astraeus.net.packet.outgoing.impl.SendSideBarInterface;
import main.astraeus.net.protocol.codec.IsaacRandomPair;
import main.astraeus.utility.Decodeable;
import main.astraeus.utility.Encodeable;
import main.astraeus.utility.StringUtils;

public final class Player extends MobileEntity {

      private ChatMessage chatMessage = new ChatMessage();

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
       * The flag that denotes a player disconnected.
       */
      private boolean disconnected;

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
       * The flag that denotes changing a region.
       */
      private boolean regionChange;

      /**
       * The flag that denotes a player is teleporting.
       */
      private boolean teleporting;
      
      /**
       * The flag that denotes this player is in debug mode.
       */
      private boolean debugMode;

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
            FACE_COORDINATE,
            FACING_COORDINATE_X,
            FACING_COORDINATE_Y;
      }

      /**
       * Displays server debug messages to admins and developers.
       */
      private boolean serverDebug = false;

      /**
       * The overloaded class constructor used for instantiation of this class file.
       * 
       * @param context The context of this player's channel.
       */
      public Player(PlayerChannel context) {
            this.context = context;
      }

      public boolean isBusy() {
            if (((Boolean) getAttributes().get(Attributes.BANKING))
                        || (Boolean) getAttributes().get(Attributes.SHOPPING)
                        || (Boolean) getAttributes().get(Attributes.TRADING)) {
                  return true;
            }
            return false;
      }

      /**
       * Creates all the in-game tabs for a player.
       * 
       * @param The instance of this encoder.
       */
      public void sendTabs() {
            for (int index = 0; index < GameConstants.SIDE_BARS.length; index++) {
                  send(new SendSideBarInterface(index, GameConstants.SIDE_BARS[index]));
            }
      }

      /**
       * Sends a message into this players chatbox.
       */
      public void sendMessage(String message) {
            send(new SendMessage(message));
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
      public PlayerRights getRights() {
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
      public final void send(OutgoingPacket packet) {
            getContext().execute(new WriteChannelEvent(packet.getHeader(), packet.encode(this)));
      }

      /**
       * Executes a writable event.
       * 
       * @param event The writable event to execute.
       * 
       *        {@code true} If this operation can be performed, {@code false} otherwise.
       */
      public final boolean executeWritableEvent(Encodeable event) {
            return event.serialize();
      }

      /**
       * Executes a readable event.
       * 
       * @param event The readable event to execute.
       * 
       *        {@code true} If this operation can be performed, {@code false} otherwise.
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
       * Returns an instance of the pair of cryptography algorithms for encoding and decoding.
       *
       * @return The returned instance.
       */
      public IsaacRandomPair getIsaacRandomPair() {
            return isaacRandomPair;
      }

      /**
       * Modifies the instance of the pair of cryptography algorithms for encoding and decoding.
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
       *        {@code true} debug mode on, {@code false} otherwise.
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
            return StringUtils.capitalizePlayerName(details.getUsername()) + " "
                        + details.getAddress();
      }

      @Override
      public void dispose() {
            send(new SendLogout());
      }

      @Override
      public int getCurrentHealth() {
            return 0;
      }

      /**
       * @return the regionChange
       */
      public boolean isRegionChange() {
            return regionChange;
      }

      /**
       * @param regionChange the regionChange to set
       */
      public void setRegionChange(boolean regionChange) {
            this.regionChange = regionChange;
      }

      /**
       * @return the teleporting
       */
      public boolean isTeleporting() {
            return teleporting;
      }

      /**
       * @param teleporting the teleporting to set
       */
      public void setTeleporting(boolean teleporting) {
            this.teleporting = teleporting;
      }

      /**
       * @return the disconnected
       */
      public boolean isDisconnected() {
            return disconnected;
      }

      /**
       * @param disconnected the disconnected to set
       */
      public void setDisconnected(boolean disconnected) {
            this.disconnected = disconnected;
      }

      /**
       * @return the chatMessage
       */
      public ChatMessage getChatMessage() {
            return chatMessage;
      }

      /**
       * @param chatMessage the chatMessage to set
       */
      public void setChatMessage(ChatMessage chatMessage) {
            this.chatMessage = chatMessage;
      }
      
      /**
       * @return the debugMode
       */
      public boolean isDebugMode() {
            return debugMode;
      }

      /**
       * @param debugMode the debugMode to set
       */
      public void setDebugMode(boolean debugMode) {
            this.debugMode = debugMode;
      }  

      @Override
      public Type entityType() {
            return Type.PLAYER;
      }   

}
