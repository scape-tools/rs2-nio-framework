package com.runescape.core.net.channel.events;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import com.runescape.core.net.channel.PlayerIO;
import com.runescape.core.net.channel.protocol.codec.login.LoginRequestDecoder;
import com.runescape.core.net.channel.ChannelEvent;

public final class AcceptChannelEvent extends ChannelEvent {

	/**
	 * A multiplexor for the validation and identification of key based
	 * network events.
	 */
	private final Selector selector;

	/**
	 * The overloaded class constructor used for instantiation of this
	 * class file.
	 * 
	 * @param selector A multiplexor for the validation and identification of
	 * key based network events.
	 */
	public AcceptChannelEvent(Selector selector) {

		this.selector = selector;
	}

	@Override
	public void execute(PlayerIO context) throws IOException {

		context.getChannel().configureBlocking(false);

		final SelectionKey selectedKey = context.getChannel().register(selector, SelectionKey.OP_READ, context);

		context.setSelectedKey(selectedKey);

		context.setProtocolDecoder(new LoginRequestDecoder());
	}
}