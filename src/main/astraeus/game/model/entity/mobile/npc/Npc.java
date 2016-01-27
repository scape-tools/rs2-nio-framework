package main.astraeus.game.model.entity.mobile.npc;

import main.astraeus.game.model.Position;
import main.astraeus.game.model.entity.EntityEventListener;
import main.astraeus.game.model.entity.mobile.MobileEntity;

public class Npc extends MobileEntity {

      /**
       * The id of this npc.
       */
      private int id;

      private Position initialPosition;

      private final NpcEventListener listener = new NpcEventListener();

      public Npc(int id, int slot) {
            this.id = id;
            this.setSlot(slot);
      }

      public void prepare() {
            getMovement().handleEntityMovement();
      }

      /**
       * Gets the id of this npc.
       * 
       * @return id
       */
      public int getId() {
            return id;
      }

      public void setInitialPosition(Position initialPosition) {
            this.initialPosition = initialPosition;
      }

      /**
       * @return the position
       */
      public Position getPosition() {
            return initialPosition;
      }

      /**
       * @param id the id to set
       */
      public void setId(int id) {
            this.id = id;
      }

      @Override
      public EntityEventListener<Npc> getEventListener() {
            return listener;
      }

      @Override
      public void dispose() {

      }

      @Override
      public int getCurrentHealth() {
            return 0;
      }

      @Override
      public Type entityType() {
            return  Type.NPC;
      }

}
