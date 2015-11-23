package com.astraeus.core.net.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.events.PrepareChannelEvent;
import com.astraeus.core.net.channel.packet.OutgoingPacket;
import com.astraeus.core.net.channel.packet.PacketBuilder;
import com.astraeus.core.net.channel.protocol.ProtocolStateDecoder;

/**
 * A session handler that will maintain input and output operations for a player.
 * 
 * @author SeVen
 */
public final class PlayerChannel {
	
	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(PlayerChannel.class.getName());
	
	/**
	 * A token representing the registration of a channel.
	 */
	private SelectionKey selectedKey;

	/**
	 * A selected channel for stream-oriented connecting sockets. 
	 */
	private final SocketChannel channel;

	/**
	 * A dynamic buffer for reading sequences of bytes over a channel.
	 */
	private final ByteBuffer buffer = ByteBuffer.allocate(512);

	/**
	 * An instance of the player connecting through this channel.
	 */
	private final Player player = new Player(this);

	/**
	 * A state based decoder to handle the asynchronous translation of client protocol.
	 */
	private ProtocolStateDecoder protocolDecoder;

	/**
	 * The overloaded class constructor used for instantiation of this
	 * class file.
	 * 
	 * @param channel A selected channel for stream-oriented connecting sockets.
	 */
	public PlayerChannel(SocketChannel channel) {
		this.channel = channel;
	}

	/**
	 * Returns an instance of the token representing the registration 
	 * of a channel.
	 * 
	 * @return The returned instance.
	 */
	public SelectionKey getSelectedKey() {
		return selectedKey;
	}

	/**
	 * Returns an instance of the selected channel.
	 * 
	 * @return The returned instance.
	 */
	public SocketChannel getChannel() {
		return channel;
	}

	/**
	 * Returns an instance of the dynamic buffer.
	 * 
	 * @return The returned instance.
	 */
	public ByteBuffer getBuffer() {
		return buffer;
	}

	/**
	 * Returns an instance of the server's protocol decoder.
	 * 
	 * @return The returned instance.
	 */
	public ProtocolStateDecoder getProtocolDecoder() {
		return protocolDecoder;
	}

	/**
	 * Modifies the instance of the server's protocol decoder.
	 * 
	 * @param protocolDecoder The new modification.
	 */
	public void setProtocolDecoder(ProtocolStateDecoder protocolDecoder) {
		this.protocolDecoder = protocolDecoder;
	}

	/**
	 * Modifies the token representing the registration of a channel.
	 * 
	 * @param selectionKey The new modification.
	 */
	public void setSelectedKey(SelectionKey selectionKey){
		this.selectedKey = selectionKey;
	}

	/**
	 * Returns an instance of the player connecting through this
	 * channel.
	 * 
	 * @return The returned instance.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Prepares the channel's internal buffer for the writing of a message.
	 * 
	 * @param builder The message of interest.
	 * 
	 * @param buffer The internal buffer.
	 */
	public final void prepare(OutgoingPacket packet, PacketBuilder builder) {
		execute(new PrepareChannelEvent(packet.getHeader(), builder, packet.getOpcode()));
	}

	/**
	 * Executes a single channel pertinent event.
	 * 
	 * @param event The event receiving the execution.
	 */
	public final void execute(ChannelEvent event) {
		try {
			event.execute(this);
		} catch (IOException exception) {
			close();
		}
	}

	/**
	 * Forces an existing channel's connection to close.
	 */
	public void close() {
		try {			
			if(player.getContext().getSelectedKey().isValid()) {
			
			/*
			 * Requests that the registration of this key's channel with its selector be cancelled.
			 */
			selectedKey.cancel();
			
			/*
			 * Removes the player from the virtual world.
			 */
			player.getEventListener().remove(player);

			/*
			 * Informs the user of the removal of the channel's connection.
			 */
			logger.log(Level.INFO, "Closed : " + channel.getLocalAddress() + ".");
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}