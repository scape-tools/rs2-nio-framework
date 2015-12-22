package main.astraeus.core.net.channel.events;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import main.astraeus.core.net.channel.ChannelEvent;
import main.astraeus.core.net.channel.PlayerChannel;
import main.astraeus.core.net.channel.protocol.codec.login.LoginRequestDecoder;

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
	public void execute(PlayerChannel context) throws IOException {		
		context.getChannel().configureBlocking(false);		
		final SelectionKey selectedKey = context.getChannel().register(selector, SelectionKey.OP_READ, context);
		context.setSelectedKey(selectedKey);		
		context.setProtocolDecoder(new LoginRequestDecoder());
	}
}