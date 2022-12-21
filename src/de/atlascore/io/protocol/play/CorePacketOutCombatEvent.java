package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutCombatEvent;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCombatEvent extends CoreAbstractHandler<PacketOutCombatEvent> {

	@Override
	public void read(PacketOutCombatEvent packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		int event = readVarInt(in);
		packet.setEvent(event);
		if (event == 0) 
			return;
		if (event == 1) {
			packet.setDuration(readVarInt(in));
			packet.setEntityID(in.readInt());
		} else {
			packet.setPlayerID(readVarInt(in));
			packet.setEntityID(in.readInt());
			packet.setDeathMessage(readString(in));
		}
	}

	@Override
	public void write(PacketOutCombatEvent packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		int event = packet.getEvent();
		writeVarInt(event, out);
		if (event == 0) 
			return;
		if (event == 1) {
			writeVarInt(packet.getDuration(), out);
			out.writeInt(packet.getEntityID());
		} else {
			writeVarInt(packet.getPlayerID(), out);
			out.writeInt(packet.getEntityID());
			writeString(packet.getDeathMessage(), out);
		}
	}

	@Override
	public PacketOutCombatEvent createPacketData() {
		return new PacketOutCombatEvent();
	}

}
