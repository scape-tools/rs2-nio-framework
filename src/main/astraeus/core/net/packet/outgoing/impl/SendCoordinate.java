package main.astraeus.core.net.packet.outgoing.impl;

import main.astraeus.core.game.model.Position;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.net.packet.PacketWriter;
import main.astraeus.core.net.packet.outgoing.OutgoingPacket;
import main.astraeus.core.net.protocol.codec.ByteModification;

/**
 * The {@link OutgoingPacket} that creates a new coordinate.
 * 
 * @author SeVen
 */
public class SendCoordinate extends OutgoingPacket {

      /**
       * The coordinate to create.
       */
      private final Position coordinate;

      /**
       * Creates a new {@link SendCoordinate}.
       * 
       * @param coordinate The new coordinate.
       */
      public SendCoordinate(Position coordinate) {
            super(85, 3);
            this.coordinate = coordinate;
      }

      @Override
      public PacketWriter encode(Player player) {
            player.getContext().prepare(this, writer);
            writer.writeByte(coordinate.getY() - 8 * player.getLastPosition().getRegionalY(),
                        ByteModification.NEGATION);
            writer.writeByte(coordinate.getX() - 8 * player.getLastPosition().getRegionalX(),
                        ByteModification.NEGATION);
            return writer;
      }

}
