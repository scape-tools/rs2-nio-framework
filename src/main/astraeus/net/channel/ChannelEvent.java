package main.astraeus.net.channel;

import java.io.IOException;

public abstract class ChannelEvent {

	/**
	 * Executes an event pertinent to channel operation.
	 * 
	 * @param context The context of the channel that is executing the event.
	 * 
	 * @throws IOException The exception thrown if an error occurs while performing
	 * an input or output operation involving the channel.
	 */
	public abstract void execute(PlayerChannel context) throws IOException;
}