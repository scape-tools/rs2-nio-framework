package com.astraeus.core.net.channel.packet.outgoing;

import java.nio.ByteBuffer;
import java.util.Iterator;

import com.astraeus.core.Server;
import com.astraeus.core.game.model.entity.UpdateFlags.UpdateFlag;
import com.astraeus.core.game.model.entity.mobile.npc.Npc;
import com.astraeus.core.game.model.entity.mobile.npc.update.UpdateBlock;
import com.astraeus.core.game.model.entity.mobile.npc.update.impl.NpcMovementBlock;
import com.astraeus.core.game.model.entity.mobile.npc.update.impl.StatefulUpdateBlock;
import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.OutgoingPacket;
import com.astraeus.core.net.channel.packet.PacketBuilder;
import com.astraeus.core.net.channel.packet.PacketHeader;
import com.astraeus.core.net.channel.protocol.codec.game.ByteAccess;

/**
 * The {@link OutgoingPacket} that updates a non-player character.
 * 
 * @author SeVen
 */
public class NpcUpdatePacket extends OutgoingPacket {

	/**
	 * Creates a new {@link NpcUpdatePacket}.
	 */
	public NpcUpdatePacket() {
		super(65, PacketHeader.VARIABLE_SHORT, 16384);
	}
	
	/**
	 * Appends an update to the main update block.
	 * 
	 * @param block
	 * 		The block to append.
	 * 
	 * @param builder
	 * 		The builder to create a buffer.
	 * 
	 * @param npc
	 * 		The npc to update.
	 */
	public final void append(UpdateBlock block, PacketBuilder builder, Npc npc) {
		block.update(npc, builder);
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		PacketBuilder update = new PacketBuilder(ByteBuffer.allocate(8192));
		
		builder.setAccessType(ByteAccess.BIT_ACCESS);
		builder.putBits(8, player.getLocalNpcs().size());
		for(Iterator<Npc> iterator = player.getLocalNpcs().iterator(); iterator.hasNext();) {
			Npc npc = iterator.next();
			
			if(npc.getPosition().isWithinDistance(player.getPosition(), 15) && Server.getUpdateProcessor().getNpcs().containsKey(npc.getIndex())) {
				append(new NpcMovementBlock(), builder, npc);
				if (npc.getUpdateFlags().isUpdateRequired()) {
					append(new StatefulUpdateBlock(), update, npc);
				}
			} else {
				iterator.remove();
				builder.putBits(1,  1);
				builder.putBits(2, 3);
			}
		}
		
		for(Npc npc : Server.getUpdateProcessor().getNpcs().values()) {
			if (player.getLocalNpcs().contains(npc)) {
				continue;
			}
			if (npc.getPosition().isWithinDistance(player.getPosition(), 15)) {
				player.getLocalNpcs().add(npc);
				npc.getUpdateFlags().flag(UpdateFlag.REGISTERED_LOCALLY);
				builder.putBits(14,  npc.getIndex());
				builder.putBits(5,  npc.getPosition().getY() - player.getPosition().getY());
				builder.putBits(5, npc.getPosition().getX() - player.getPosition().getY());
				builder.putBits(1, 1);
				builder.putBits(12, npc.getId());
				builder.putBits(1, 1);
				if(npc.getUpdateFlags().isUpdateRequired()) {
					append(new StatefulUpdateBlock(), update, npc);
				}
			}
		}
		if(update.getPosition() > 0) {
			builder.putBits(14, 16383);
			builder.setAccessType(ByteAccess.BYTE_ACCESS);
			builder.putBytes(update.getBuffer());
		} else {
			builder.setAccessType(ByteAccess.BYTE_ACCESS);
		}
		builder.endVariableShortPacketHeader();		
		return builder;
	}


}
