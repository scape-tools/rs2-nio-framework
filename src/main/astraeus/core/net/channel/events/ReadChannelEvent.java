package main.astraeus.core.net.channel.events;

import java.io.IOException;

import main.astraeus.core.net.channel.ChannelEvent;
import main.astraeus.core.net.channel.PlayerChannel;

public final class ReadChannelEvent extends ChannelEvent {

	@Override
	public void execute(PlayerChannel context) {
		try {

			/*
			 * Determines if the channel produces a valid sequence of bytes.
			 */
			if (context.getChannel().read(context.getBuffer()) != -1) {

				/*
				 * The limit is set to the current position and then the position is set to zero.
				 */
				context.getBuffer().flip();

				/*
				 * Performs the protocol translations.
				 */
				context.getProtocolDecoder().decode(context);

				/*
				 * Clears this buffer. The position is set to zero, the limit is set to the capacity.
				 */
				context.getBuffer().clear();
			}
		} catch (IOException exception) {
			context.close();
		}
	}
}