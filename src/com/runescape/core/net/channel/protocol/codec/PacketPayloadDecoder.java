package com.runescape.core.net.channel.protocol.codec;

import java.io.IOException;
import java.util.logging.Logger;

import com.runescape.core.net.channel.PlayerIO;
import com.runescape.core.net.channel.protocol.ProtocolStateDecoder;

public final class PacketPayloadDecoder extends ProtocolStateDecoder {

	@Override
	public void decode(PlayerIO context) throws IOException {

	}

	@Override
	public void error(PlayerIO context, String description) throws IOException {

		Logger.getLogger(PacketPayloadDecoder.class.getCanonicalName()).info(description);
	}
}