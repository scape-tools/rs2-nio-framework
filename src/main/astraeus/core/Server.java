package main.astraeus.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.logging.Logger;

import main.astraeus.core.game.processor.ScheduledProcessor;
import main.astraeus.core.game.processor.impl.ScheduledNetworkProcessor;
import main.astraeus.core.game.processor.impl.ScheduledUpdateProcessor;
import main.astraeus.core.net.channel.packet.incoming.IncomingPacketRegistration;
import main.astraeus.utility.startup.ItemDefinitionLoader;
import main.astraeus.utility.startup.ShopLoader;

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
	 * The flag to check if the server has successfully started.
	 */
	public static boolean SERVER_STARTED = false;

	/**
	 * A periodical processor for the concurrent updating of players in the
	 * virtual world.
	 */
	private static final ScheduledUpdateProcessor updateProcessor = new ScheduledUpdateProcessor();

	/**
	 * The main starting point of the server.
	 * 
	 * @param args
	 *            The command line arguments.
	 */
	public static void main(String... args) throws IOException {

		new IncomingPacketRegistration();
			
		new ShopLoader();
		
		new ItemDefinitionLoader();
		
		bind();
	}

	/**
	 * Binds the {@link ServerSocketChannel} to a specified address and port
	 */
	public static void bind() throws IOException {

		final ServerSocketChannel channel = ServerSocketChannel.open();
		final Selector selector = Selector.open();

		channel.configureBlocking(false);
		channel.register(selector, SelectionKey.OP_ACCEPT);

		final ScheduledProcessor netProcessor = new ScheduledNetworkProcessor(
				selector, channel);

		channel.bind(new InetSocketAddress(Configuration.ADDRESS,
				Configuration.PORT));

		netProcessor.start(0);

		updateProcessor.start(0);
		logger.info(Configuration.SERVER_NAME + " has bound to " + channel.socket().getInetAddress() + " on port " + channel.socket().getLocalPort() + ".");
		SERVER_STARTED = true;
	}

	/**
	 * Returns an instance of the {@link ScheduledUpdateProcessor} that is
	 * responsible for the concurrent updating of mobile actors in the virtual
	 * world.
	 * 
	 * @return The returned instance.
	 */
	public static ScheduledUpdateProcessor getUpdateProcessor() {
		return updateProcessor;
	}
}