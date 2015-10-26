package com.runescape.core.net.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.runescape.core.game.model.entity.character.player.Player;
import com.runescape.core.net.NetworkConstants;
import com.runescape.core.net.channel.events.PrepareChannelEvent;
import com.runescape.core.net.channel.message.Packet;
import com.runescape.core.net.channel.message.PacketBuilder;
import com.runescape.core.net.channel.message.incoming.PacketListener;
import com.runescape.core.net.channel.protocol.ProtocolStateDecoder;

/**
 * A session handler that will maintain input and output operations for a player.
 * 
 * @author SeVen
 */
public final class PlayerIO {
	
	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(PlayerIO.class.getName());

	/**
	 * Holds packets that need to be prioritized.
	 */
	private final Queue<Packet> prioritizedPacketQueue = new ConcurrentLinkedQueue<>();
	/**
	 * Holds packets to be handled on the next sequence.
	 */
	private final Queue<Packet> packetQueue = new ConcurrentLinkedQueue<>();
	
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
	public PlayerIO(SocketChannel channel) {
		this.channel = channel;
	}
	
	/**
	 * Processes all of the queued Packets by polling the queue, and them processes
	 * them via the handleInputMessage. 
	 */
	public void handleQueuedPackets() {
		handlePrioritizedPacketQueue();
		Packet msg;
		while((msg = packetQueue.poll()) != null) {
			handleIncomingPacket(msg);
		}	
	}
	
	/**
	 * Processes packets that need to be prioritized.
	 */
	public void handlePrioritizedPacketQueue() {
		Packet msg;
		while((msg = prioritizedPacketQueue.poll()) != null) {
			handleIncomingPacket(msg);
		}
	}
	
	/**
	 * Processes incoming packets from the {@link SocketChannel}.
	 */
	public void handleIncomingPacket(Packet msg) {
		int opcode = msg.getOpcode();
		PacketListener listener = NetworkConstants.PACKETS[opcode];
		
		if (msg.getLength() != NetworkConstants.PACKET_SIZES[opcode]) {
			System.out.println("Invalid Incoming Packet: " + msg.getOpcode() + " Length: " + msg.getLength() + " Actual: "  + msg.getLength());
			return;
		}
		listener.handleMessage(player, msg);
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
	public final void prepare(PacketBuilder builder) {
		execute(new PrepareChannelEvent(builder.getHeader(), builder, builder.getOpcode()));
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

		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}