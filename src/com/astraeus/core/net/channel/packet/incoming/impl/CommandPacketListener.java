package com.astraeus.core.net.channel.packet.incoming.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.IncomingPacket;
import com.astraeus.core.net.channel.packet.incoming.IncomingPacketListener;
import com.astraeus.core.net.channel.packet.incoming.IncomingPacketOpcode;

/**
 * The incoming {@link IncomingPacket} responsible for handling user commands send from the client.
 * 
 * @author SeVen
 */
@IncomingPacketOpcode( 103 )
public class CommandPacketListener implements IncomingPacketListener {

	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(CommandPacketListener.class.getName());
	
	@Override
	public void handleMessage(Player player, IncomingPacket packet) {
		final int messageLength = packet.getLength() - 1;

		byte message[] = packet.readBytes(messageLength);
		
		String command[] = new String(message).split(" ");
		
		switch(command[0]) {
		
		case "test":
			player.sendMessage("This worked!");
			break;
			
		case "debug":
			player.setServerDebug(!player.isServerDebug() ? true : false);
			player.sendMessage(player.isServerDebug() ? "[DEBUG MODE] - ON" : "[DEBUG MODE] - OFF");
			break;
			
		case "region":
			player.getPacketSender().sendRegionalUpdate();
			break;
			
		default:
			logger.log(Level.INFO, String.format("Unknown command: %s", command[0]));
			break;
		}
	}

}
