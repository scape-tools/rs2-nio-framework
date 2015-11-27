package com.astraeus.core.net.channel.packet.outgoing;

import java.nio.ByteBuffer;
import java.util.Iterator;

import com.astraeus.core.Server;
import com.astraeus.core.game.model.entity.mobile.npc.Npc;
import com.astraeus.core.game.model.entity.mobile.npc.update.NpcUpdating;
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

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		PacketBuilder update = new PacketBuilder(ByteBuffer.allocate(8192));
		
		builder.setAccessType(ByteAccess.BIT_ACCESS);
		builder.putBits(8, player.getLocalNpcs().size());
		for(Iterator<Npc> iterator = player.getLocalNpcs().iterator(); iterator.hasNext();) {
			Npc npc = iterator.next();
			
			if(npc.getPosition().isWithinDistance(player.getPosition(), 15) && Server.getUpdateProcessor().getNpcs().containsKey(npc.getIndex())) {
				NpcUpdating.updateMovement(npc, builder);
				if (npc.getUpdateFlags().isUpdateRequired()) {
					NpcUpdating.appendUpdates(npc, update);
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
				NpcUpdating.addNPC(npc, player, update);
				if(npc.getUpdateFlags().isUpdateRequired()) {
					NpcUpdating.appendUpdates(npc, update);
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
