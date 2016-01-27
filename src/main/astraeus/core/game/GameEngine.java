package main.astraeus.core.game;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import main.astraeus.core.Configuration;
import main.astraeus.core.game.service.ScheduledService;
import main.astraeus.core.game.service.impl.ScheduledNetworkService;
import main.astraeus.core.game.service.impl.ScheduledUpdateService;
import main.astraeus.core.net.packet.incoming.IncomingPacketRegistration;
import main.astraeus.utility.startup.ItemDefinitionLoader;
import main.astraeus.utility.startup.PacketSizeLoader;

/**
 * The {@link GameEngine} that runs and keeps the game in sync.
 * 
 * @author SeVen
 */
public class GameEngine {
      
      /**
       * The single logger for this class.
       */
      private static final Logger LOGGER = Logger.getLogger(GameEngine.class.getName());      

      /**
       * The service that executes game tasks.
       */
      private final ScheduledUpdateService gameService = new ScheduledUpdateService();
      
      /**
       * The service that executes start up tasks.
       */
      private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
      
      /**
       * The flag to check if the server has successfully started.
       */
      public static boolean SERVER_STARTED = false;
      
      public GameEngine build() throws Exception {
            LOGGER.info("Preparing Game Engine... ");
            
            startup();
            
            gameService.start(0);  

            service.shutdown();
            
            if (!service.awaitTermination(15, TimeUnit.MINUTES)) {
                  throw new IllegalStateException("The background service load took too long!");
            }
            
            LOGGER.info("Game engine has successfully been built");
            return this;
      }
      
      /**
       * Binds the {@link ServerSocketChannel} to a specified address and port
       */
      public GameEngine bind() throws IOException {
        LOGGER.info("Building network");
        final ServerSocketChannel channel = ServerSocketChannel.open();
        final Selector selector = Selector.open();

        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);

        final ScheduledService netProcessor = new ScheduledNetworkService(selector, channel);

        channel.bind(new InetSocketAddress(Configuration.ADDRESS, Configuration.PORT));

        netProcessor.start(0);
        
        SERVER_STARTED = true;
        LOGGER.info(String.format("%s has bound to %s on port %d", Configuration.SERVER_NAME, channel.socket().getInetAddress(), channel.socket().getLocalPort()));
        return this;
      }
      
      
      public void startup() {
          LOGGER.info("Loading startup services..");
          service.execute(() ->  {
                new PacketSizeLoader();                
                new IncomingPacketRegistration();                
          });
            
          service.execute(() -> new ItemDefinitionLoader());
          LOGGER.info("Loaded startup services");
      }

      
      public String toString() {
            return "[GAME ENGINE]";
      }

}
