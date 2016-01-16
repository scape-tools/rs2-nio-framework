package main.astraeus.core.net.packet.outgoing.impl;

import java.nio.ByteBuffer;
import java.util.Iterator;

import main.astraeus.core.game.World;
import main.astraeus.core.game.model.Position;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.player.update.mask.PlayerAnimationUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.player.update.mask.PlayerAppearanceUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.player.update.mask.PlayerChatUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.player.update.mask.PlayerDoubleHitUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.player.update.mask.PlayerFaceCoordinateUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.player.update.mask.PlayerForceChatUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.player.update.mask.PlayerForceMovementUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.player.update.mask.PlayerGraphicUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.player.update.mask.PlayerInteractionUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.player.update.mask.PlayerSingleHitUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.packet.PacketBuilder;
import main.astraeus.core.net.packet.PacketHeader;
import main.astraeus.core.net.packet.outgoing.OutgoingPacket;
import main.astraeus.core.net.protocol.codec.ByteAccess;

/**
 * The {@link OutgoingPacket} that will update a player in the game world.
 * 
 * @author SeVen
 */
public final class SendPlayerUpdate extends OutgoingPacket {
	
    /**
     * The max amount of players that can be added per cycle.
     */
    private static final int MAX_NEW_PLAYERS_PER_CYCLE = 25;

    /**
     * Creates a new {@link SendPlayerUpdate}.
     */
	public SendPlayerUpdate() {
		super(81, PacketHeader.VARIABLE_SHORT, 16384);
	}

	@Override
	public PacketBuilder encode(Player player) {		
		if(player.isRegionChange()) {
			player.send(new SendRegionUpdate());
		}
		
		PacketBuilder update = new PacketBuilder(ByteBuffer.allocate(8192));
		
		player.getContext().prepare(this, builder);

		builder.setAccessType(ByteAccess.BIT_ACCESS);

		updateMovement(player, builder);

		if (player.getUpdateFlags().isUpdateRequired()) {
			appendUpdates(player, update, false, true);
		}

		builder.putBits(8, player.getLocalPlayers().size());

		for (Iterator<Player> iterator = player.getLocalPlayers().iterator(); iterator.hasNext();) {

			final Player other = iterator.next();

			if (World.getPlayers()[other.getSlot()] != null && other.isRegistered() && other.getPosition().isWithinDistance(player.getPosition(), Position.VIEWING_DISTANCE)) {
				updatePlayerMovement(other, builder);
				
				if (other.getUpdateFlags().isUpdateRequired()) {
				    appendUpdates(other, update, false, false);
				}
			} else {
				iterator.remove();
				builder.putBit(true);
				builder.putBits(2, 3);
			}
		}
		
		int playersAdded = 0;

		for (Player other : World.getPlayers()) {
			
		    if (other == null || !other.isRegistered() || other == player || player.getLocalPlayers().contains(other)) {
				continue;
			}

			if (other.getLocalPlayers().size() >= 79 || playersAdded > MAX_NEW_PLAYERS_PER_CYCLE) {
				break;
			}
			
			if (other.getPosition().isWithinDistance(player.getPosition(), 15)) {
				addPlayer(player, other, builder);
				appendUpdates(other, update, true, false);
				playersAdded++;
			}			
		}
		
		if (update.getBuffer().position() > 0) {
			builder.putBits(11, 2047)
			.setAccessType(ByteAccess.BYTE_ACCESS)
			.putBytes(update.getBuffer());
		} else {
			builder.setAccessType(ByteAccess.BYTE_ACCESS);
		}

		return builder;
	}
	
    /**
     * Adds a players to the local player list, and in-view of other players.
     * 
     * @param player
     *            The player to add.
     * 
     * @param other
     *            The other player that will be viewing this player.
     * 
     * @param builder
     *            The builder used to write and store data.
     */
	public void addPlayer(Player player, Player other, PacketBuilder builder) {
		player.getLocalPlayers().add(other);		
		builder.putBits(11, other.getSlot())
		.putBit(true)
		.putBit(true)
		.putBits(5, other.getPosition().getY() - player.getPosition().getY())
		.putBits(5, other.getPosition().getX() - player.getPosition().getX());
	}
	
	public void updateMovement(Player player, PacketBuilder buffer) {		
		/*
		 * Check if the player is teleporting.
		 */
		if (player.isTeleporting() || player.isRegionChange()) {
			
		    /*
		     * They are, so an update is required.
		     */
			buffer.putBit(true);
			
		    /*
		     * This value indicates the player teleported.
		     */	 
			buffer.putBits(2, 3);
			
		    /*
		     * This is the new player height.
		     */
			buffer.putBits(2, player.getPosition().getHeight());
			
		    /*
		     * This indicates that the client should discard the walking queue.
		     */
			buffer.putBits(1, player.isTeleporting() ? 1 : 0);
			
		    /*
		     * This flag indicates if an update block is appended.
		     */
			buffer.putBits(1, player.getUpdateFlags().isUpdateRequired() ? 1 : 0);
			
			/*
			 * The local Y position of this player.
			 */			
			buffer.putBits(7, player.getPosition().getLocalY(player.getLastPosition()));
			
			/*
			 * The local X position of this player.
			 */	
			buffer.putBits(7, player.getPosition().getLocalX(player.getLastPosition()));
		} else {
		    /*
		     * Otherwise, check if the player moved.
		     */
			if (player.getWalkingDirection() == -1) {
				
				/*
				 * The player didn't move. Check if an update is required.
				 */
				if (player.getUpdateFlags().isUpdateRequired()) {
				    /*
				     * Signifies an update is required.
				     */
					buffer.putBit(true);
					
				    /*
				     * But signifies that we didn't move.
				     */
					buffer.putBits(2, 0);
				} else {					
				    /*
				     * Signifies that nothing changed.
				     */
					buffer.putBits(1, 0);
				}
			} else {				
				/*
				 * Check if the player was running.
				 */
				if (player.getRunningDirection() == -1) {
				    /*
				     * The player walked, an update is required.
				     */
					buffer.putBit(true);
					
				    /*
				     * This indicates the player only walked.
				     */
					buffer.putBits(2, 1);
					
				    /*
				     * This is the player's walking direction.
				     */
					buffer.putBits(3, player.getWalkingDirection());
					
				    /*
				     * This flag indicates an update block is appended.
				     */
					buffer.putBits(1, player.getUpdateFlags().isUpdateRequired() ? 1 : 0);
				} else {					
				    /*
				     * The player ran, so an update is required.
				     */
					buffer.putBit(true);
					
				    /*
				     * This indicates the player ran.
				     */
					buffer.putBits(2, 2);
					
				    /*
				     * This is the walking direction.
				     */
					buffer.putBits(3, player.getWalkingDirection());
					
				    /*
				     * And this is the running direction.
				     */
					buffer.putBits(3, player.getRunningDirection());
					
				    /*
				     * And this flag indicates an update block is appended.
				     */
					buffer.putBits(1, player.getUpdateFlags().isUpdateRequired() ? 1 : 0);
				}
			}
		}
	}
	
    /**
     * Updates a non-this player's movement.
     * 
     * @param builder
     *            The packet.
     * @param otherPlayer
     *            The player.
     */
    public static void updatePlayerMovement(Player player, PacketBuilder builder) {
	/*
	 * Check which type of movement took place.
	 */
	if (player.getWalkingDirection() == -1) {
	    /*
	     * If no movement did, check if an update is required.
	     */
	    if (player.getUpdateFlags().isUpdateRequired()) {
		/*
		 * Signify that an update happened.
		 */
		builder.putBit(true);

		/*
		 * Signify that there was no movement.
		 */
		builder.putBits(2, 0);
	    } else {
		/*
		 * Signify that nothing changed.
		 */
		builder.putBit(false);
	    }
	} else if (player.getRunningDirection() == -1) {
	    /*
	     * The player moved but didn't run. Signify that an update is
	     * required.
	     */
	    builder.putBit(true);

	    /*
	     * Signify we moved one tile.
	     */
	    builder.putBits(2, 1);

	    /*
	     * Write the primary sprite (i.e. walk direction).
	     */
	    builder.putBits(3, player.getWalkingDirection());

	    /*
	     * Write a flag indicating if a block update happened.
	     */
	    builder.putBit(player.getUpdateFlags().isUpdateRequired());
	} else {
	    /*
	     * The player ran. Signify that an update happened.
	     */
	    builder.putBit(true);

	    /*
	     * Signify that we moved two tiles.
	     */
	    builder.putBits(2, 2);

	    /*
	     * Write the primary sprite (i.e. walk direction).
	     */
	    builder.putBits(3, player.getWalkingDirection());

	    /*
	     * Write the secondary sprite (i.e. run direction).
	     */
	    builder.putBits(3, player.getRunningDirection());

	    /*
	     * Write a flag indicating if a block update happened.
	     */
	    builder.putBit(player.getUpdateFlags().isUpdateRequired());
	}
    }
	
    /**
     * Appends pieces of a players update block to the main player update block.
     * 
     * @param block
     *            The update block to append.
     * 
     * @param player
     *            The player to append the update for.
     * 
     * @param builder
     *            The buffer to store data.
     */
	public void append(PlayerUpdateBlock block, Player player, PacketBuilder builder) {
		block.encode(player, builder);		
	}
	
    /**
     * Updates the state of a player.
     * 
     * @param player
     *            The player to update the state for.
     * 
     * @param builder
     *            The builder that constructs a packet and stores the data.
     *            
     * @param forceAppearance
     * 		The flag that determines if this player should be forcefully updated.
     */
	public void appendUpdates(Player player, PacketBuilder buffer, boolean forceAppearance, boolean noChat) {		
		synchronized(player) {
			
			if (!player.getUpdateFlags().isUpdateRequired() && !forceAppearance) {	    
			    return;
			}
			
			int mask = 0x0;
			
			if (player.getUpdateFlags().get(UpdateFlag.APPEARANCE) || forceAppearance) {
				mask |= 0x10;
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.GRAPHICS)) {
			    mask |= 0x100;
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.ANIMATION)) {
			    mask |= 0x8;
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.FORCED_CHAT) && player.getForcedChat().length() > 0) {
			    mask |= 0x4;
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.CHAT) && !noChat) {
			    mask |= 0x80;
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.ENTITY_INTERACTION)) {
			    mask |= 0x1;
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.FACE_COORDINATE)) {
			    mask |= 0x2;
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.SINGLE_HIT)) {
			    mask |= 0x20;
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.SINGLE_HIT)) {
			    mask |= 0x20;
			}

			if (player.getUpdateFlags().get(UpdateFlag.DOUBLE_HIT)) {
			    mask |= 0x200;
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.FORCE_MOVEMENT)) {
			    mask |= 0x400;
			}
			
			if (mask >= 0x100) {
				mask |= 0x40;
				buffer.put((mask & 0xFF))
				.put((mask >> 8));
			} else {
				buffer.put(mask);
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.GRAPHICS)) {
			    append(new PlayerGraphicUpdateBlock(), player, builder);
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.ANIMATION)) {
			    append(new PlayerAnimationUpdateBlock(), player, builder);
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.FORCED_CHAT) && player.getForcedChat().length() > 0) {
			    append(new PlayerForceChatUpdateBlock(), player, builder);
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.CHAT) && !noChat) {
			    append(new PlayerChatUpdateBlock(), player, builder);
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.ENTITY_INTERACTION)) {
			    append(new PlayerInteractionUpdateBlock(), player, builder);
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.APPEARANCE) || forceAppearance) {				
				append(new PlayerAppearanceUpdateBlock(), player, buffer);
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.FORCE_MOVEMENT)) {
			    append(new PlayerForceMovementUpdateBlock(), player, builder);
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.FACE_COORDINATE)) {
			    append(new PlayerFaceCoordinateUpdateBlock(), player, builder);
			}
			
			if (player.getUpdateFlags().get(UpdateFlag.SINGLE_HIT)) {
			    append(new PlayerSingleHitUpdateBlock(), player, builder);
			}

			if (player.getUpdateFlags().get(UpdateFlag.DOUBLE_HIT)) {
			    append(new PlayerDoubleHitUpdateBlock(), player, builder);
			}
		}
	}
	
}