package main.astraeus;

/**
 * The core class for the server.
 * 
 * @author SeVen
 */
public final class Server {

      /**
       * The main starting point of the server.
       * 
       * @param args The command line arguments.
       * @throws Exception 
       */
      public static void main(String... args) throws Exception {
            new Bootstrap().build().bind();
      }

}
