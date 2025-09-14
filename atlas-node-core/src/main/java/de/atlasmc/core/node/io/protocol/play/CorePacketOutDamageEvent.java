package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutDamageEvent;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDamageEvent implements PacketIO<PacketOutDamageEvent> {

	@Override
	public void read(PacketOutDamageEvent packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.entityID = readVarInt(in);
		packet.damageType = readVarInt(in);
		packet.sourceCauseID = readVarInt(in);
		packet.sourceDirectID = readVarInt(in);
		if (packet.hasPosition = in.readBoolean()) {
			packet.x = in.readDouble();
			packet.y = in.readDouble();
			packet.z = in.readDouble();
		}
		
	}

	@Override
	public void write(PacketOutDamageEvent packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.entityID, out);
		writeVarInt(packet.damageType, out);
		writeVarInt(packet.sourceCauseID, out);
		writeVarInt(packet.sourceDirectID, out);
		out.writeBoolean(packet.hasPosition);
		if (packet.hasPosition) {
			out.writeDouble(packet.x);
			out.writeDouble(packet.y);
			out.writeDouble(packet.z);
		}
	}

	@Override
	public PacketOutDamageEvent createPacketData() {
		return new PacketOutDamageEvent();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutDamageEvent.class);
	}

}
