package main.astraeus.content.clicking.objects;

import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.player.PlayerRights;
import main.astraeus.game.model.entity.object.GameObject;
import main.astraeus.net.packet.outgoing.impl.SendMessage;

public class ObjectSecondClick {
      
      public static void handleAction(Player player, GameObject object) {
            
            if (player.getRights().greaterOrEqual(PlayerRights.DEVELOPER) && player.isDebugMode()) {
                  player.send(new SendMessage(String.format("[ClickObject= 2] - %s", object.toString())));
            }
            
            switch (object.getId()) {
                  
            }
      }

}
