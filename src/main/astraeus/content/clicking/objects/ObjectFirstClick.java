package main.astraeus.content.clicking.objects;

import main.astraeus.content.dialogue.Dialogues;
import main.astraeus.content.dialogue.impl.BankerDialogue;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.player.PlayerRights;
import main.astraeus.game.model.entity.object.GameObject;
import main.astraeus.net.packet.outgoing.impl.SendMessage;

public class ObjectFirstClick {

      public static void handleAction(Player player, GameObject object) {
            
            if (player.getRights().greaterOrEqual(PlayerRights.DEVELOPER) && player.isDebugMode()) {
                  player.send(new SendMessage(String.format("[ClickObject= 1] - %s", object.toString())));
            }
            
            switch (object.getId()) {
                  
                  case 2213:
                        Dialogues.sendDialogue(player, new BankerDialogue());
                        break;
                  
            }
      }
      
}
