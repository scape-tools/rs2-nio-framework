package com.runescape.core.game.processor.impl;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.runescape.core.game.processor.PeriodicalLogicProcessor;
import com.runescape.core.game.processor.PeriodicalLogicProcessorConstants;
import com.runescape.core.net.channel.PlayerIO;
import com.runescape.core.net.channel.events.AcceptChannelEvent;
import com.runescape.core.net.channel.events.ReadChannelEvent;

public final class PeriodicalNetworkProcessor extends PeriodicalLogicProcessor {

	/**
	 * A multiplexor for the validation and identification of key based
	 * network events.
	 */
	private final Selector selector;

	/**
	 * A channel for stream-oriented listening sockets.
	 */
	private final ServerSocketChannel channel;

	/**
	 * The overloaded class constructor used for the instantiation of this
	 * class file.
	 * 
	 * @param selector A multiplexor for the validation and identification of key based
	 * network events.
	 * 
	 * @param channel A channel for stream-oriented listening sockets.
	 */
	public PeriodicalNetworkProcessor(Selector selector, ServerSocketChannel channel) {
		super(PeriodicalLogicProcessorConstants.NETWORK_PROCESSOR_RATE);
		this.selector = selector;
		this.channel = channel;
	}

	@Override
	public void execute() {
		try {
			selector.selectNow();
			final Iterator<SelectionKey> iterator = selector.keys().iterator();

			while (iterator.hasNext()) {

				final SelectionKey selection = iterator.next();

				if (selection.isValid()) {
					if (selection.isAcceptable()) {
						final SocketChannel selectedChannel = channel.accept();

						if (selectedChannel != null) {
							final PlayerIO context = new PlayerIO(selectedChannel);
							context.execute(new AcceptChannelEvent(selector));
						}
					}

					if (selection.isReadable()) {
						final PlayerIO attachment = (PlayerIO) selection.attachment();
						if (attachment != null && attachment.getChannel().isOpen()) {
							attachment.execute(new ReadChannelEvent());
						}
					}
				}
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}