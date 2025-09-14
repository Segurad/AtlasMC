package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutLookAt;
import io.netty.buffer.ByteBuf;

public class CorePacketOutLookAt implements PacketIO<PacketOutLookAt> {

	@Override
	public void read(PacketOutLookAt packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.aimWithEyes = readVarInt(in) == 1;
		packet.x = in.readDouble();
		packet.y = in.readDouble();
		packet.z = in.readDouble();
		boolean hasEntity = in.readBoolean();
		packet.hasEntity = hasEntity;
		if (!hasEntity) 
			return;
		packet.entityID = readVarInt(in);
		packet.aimAtEyes = readVarInt(in) == 1;
	}

	@Override
	public void write(PacketOutLookAt packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.aimWithEyes ? 1 : 0, out);
		out.writeDouble(packet.x);
		out.writeDouble(packet.y);
		out.writeDouble(packet.z);
		out.writeBoolean(packet.hasEntity);
		if (!packet.hasEntity) 
			return;
		writeVarInt(packet.entityID, out);
		writeVarInt(packet.aimAtEyes ? 1 : 0, out);
	}

	@Override
	public PacketOutLookAt createPacketData() {
		return new PacketOutLookAt();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutLookAt.class);
	}

}
