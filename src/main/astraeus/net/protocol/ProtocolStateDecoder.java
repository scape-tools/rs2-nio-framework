package main.astraeus.net.protocol;

import java.io.IOException;

import main.astraeus.net.channel.PlayerChannel;

public interface ProtocolStateDecoder {

      /**
       * Handles the translation of the various decode stages through the login protocol.
       * 
       * @param context
       *    The context of the channel that is performing the translation.
       */
      void decode(PlayerChannel context) throws IOException;
}