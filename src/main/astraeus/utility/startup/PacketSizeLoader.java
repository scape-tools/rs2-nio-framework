package main.astraeus.utility.startup;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import main.astraeus.core.Configuration;
import main.astraeus.core.net.NetworkConstants;
import main.astraeus.utility.JsonLoader;


/**
 * Parses through the packet sizes file and associates their opcode with a size.
 * 
 * @author SeVen
 */
public final class PacketSizeLoader extends JsonLoader {

      public static final Logger logger = Logger.getLogger(PacketSizeLoader.class.getName());

      private int count = 0;

      /**
       * Creates a new {@link PacketSizeLoader}.
       */
      public PacketSizeLoader() {
            super(Configuration.DATA + "/net/packet_sizes.json");
            load();
            logger.info(String.format("Loaded: %d packet sizes", count));
      }

      @Override
      public void load(JsonObject reader, Gson builder) {
            final int opcode = reader.get("opcode").getAsInt();
            final int size = reader.get("size").getAsInt();
            count++;
            NetworkConstants.PACKET_SIZES[opcode] = size;
      }
}
