package com.runescape.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.logging.Logger;

import com.runescape.core.game.processor.PeriodicalLogicProcessor;
import com.runescape.core.game.processor.impl.PeriodicalNetworkProcessor;
import com.runescape.core.game.processor.impl.PeriodicalUpdateProcessor;

/**
 * The core class for the server.
 * 
 * @author SeVen
 */
public final class Server {

	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger
			.getLogger(Server.class.getName());

	/**
	 * A periodical processor for the concurrent updating of players in the
	 * virtual world.
	 */
	private static final PeriodicalUpdateProcessor updateProcessor = new PeriodicalUpdateProcessor();

	/**
	 * The main starting point of the server.
	 * 
	 * @param args
	 *            The command line arguments.
	 */
	public static void main(String... args) throws IOException {
		bind();
	}

	/**
	 * Binds the {@link ServerSocketChannel} to a specified address and port
	 */
	public static void bind() throws IOException {
		/*
		 * A selectable channel for stream-oriented listening sockets.
		 */
		final ServerSocketChannel channel = ServerSocketChannel.open();
		/*
		 * A multiplexor of SelectableChannel objects.
		 */
		final Selector selector = Selector.open();
		/*
		 * Configures this channels blocking mode
		 * 
		 * A Select-able channel is either in blocking mode or in non-blocking
		 * mode.
		 * 
		 * In blocking mode, every I/O operation invoked upon the channel will
		 * block until it completes. In non-blocking mode an I/O operation will
		 * never block and may transfer fewer bytes than were requested or
		 * possibly no bytes at all. The blocking mode of a selectable channel
		 * may be determined by invoking its isBlocking method.
		 */
		channel.configureBlocking(false);
		/*
		 * Registers this channel with the given selector, returning a selection
		 * key.
		 */
		channel.register(selector, SelectionKey.OP_ACCEPT);

		final PeriodicalLogicProcessor netProcessor = new PeriodicalNetworkProcessor(
				selector, channel);

		/*
		 * Fixes the channel to a specified end-point.
		 */
		channel.bind(new InetSocketAddress(Configuration.ADDRESS,
				Configuration.PORT));

		/*
		 * Starts the processor responsible for processing network's IO logic.
		 */
		netProcessor.start(0);

		/*
		 * Starts the processor responsible for processing world's updating
		 * logic.
		 */
		updateProcessor.start(0);
		logger.info(Configuration.SERVER_NAME + " has bound to " + channel.socket().getInetAddress() + " on port " + channel.socket().getLocalPort() + ".");
	}

	/**
	 * Returns an instance of the {@link PeriodicalUpdateProcessor} that is
	 * responsible for the concurrent updating of mobile actors in the virtual
	 * world.
	 * 
	 * @return The returned instance.
	 */
	public static PeriodicalUpdateProcessor getUpdateProcessor() {
		return updateProcessor;
	}
}