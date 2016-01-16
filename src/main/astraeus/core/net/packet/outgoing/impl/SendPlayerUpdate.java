package main.astraeus.core.net.packet.outgoing.impl;

import java.nio.ByteBuffer;
import java.util.Iterator;

import main.astraeus.core.Server;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.update.PlayerUpdateBlock;
import main.astraeus.core.game.model.entity.mobile.player.update.impl.PlayerAppearanceUpdateBlock;
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

			appendUpdates(player, update, false);
		}

		builder.putBits(8, player.getLocalPlayers().size());

		for (Iterator<Player> iterator = player.getLocalPlayers().iterator(); iterator.hasNext();) {

			final Player other = iterator.next();

			if (other.getPosition().isWithinDistance(player.getPosition(), 15)) {

				updateMovement(other, builder);

				if (other.getUpdateFlags().isUpdateRequired()) {

					appendUpdates(other, update, false);

				}

			} else {

				iterator.remove();

				builder.putBits(1, 1);

				builder.putBits(2, 3);
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



				appendUpdates(other, update, true);

			}
		}

		if (update.getBuffer().position() > 0) {

			builder.putBits(11, 2047);

			builder.setAccessType(ByteAccess.BYTE_ACCESS);

			builder.putBytes(update.getBuffer());

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
		
		builder.putBits(11, other.getIndex())
		.putBits(1, 1)
		.putBits(1, 1)
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
     * @param forceful
     * 		The flag that determines if this player should be forcefully updated.
     */
	public void appendUpdates(Player player, PacketBuilder buffer, boolean forceful) {		
		synchronized(player) {
			
			int mask = 0x0;
			if (player.getUpdateFlags().get(UpdateFlag.APPEARANCE) || forceful) {
				mask |= 0x10;
			}
			if (mask >= 0x100) {
				mask |= 0x40;
				buffer.putByte((mask & 0xFF));
				buffer.putByte((mask >> 8));
			} else {
				buffer.putByte((mask));
			}
			if (player.getUpdateFlags().get(UpdateFlag.APPEARANCE) || forceful) {				
				append(new PlayerAppearanceUpdateBlock(), player, buffer);
			}
		}
	}
	
}