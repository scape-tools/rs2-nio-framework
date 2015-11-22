package com.astraeus.core.game.processor.impl;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.astraeus.core.game.processor.ScheduledProcessor;
import com.astraeus.core.game.processor.ProcessorConstants;
import com.astraeus.core.net.channel.PlayerChannel;
import com.astraeus.core.net.channel.events.AcceptChannelEvent;
import com.astraeus.core.net.channel.events.ReadChannelEvent;

public final class ScheduledNetworkProcessor extends ScheduledProcessor {

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
	public ScheduledNetworkProcessor(Selector selector, ServerSocketChannel channel) {
		super(ProcessorConstants.NETWORK_PROCESSOR_RATE);
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
							final PlayerChannel context = new PlayerChannel(selectedChannel);
							context.execute(new AcceptChannelEvent(selector));
						}
					}

					if (selection.isReadable()) {
						final PlayerChannel attachment = (PlayerChannel) selection.attachment();
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