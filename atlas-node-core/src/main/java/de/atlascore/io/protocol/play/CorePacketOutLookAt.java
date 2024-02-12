package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutLookAt;
import io.netty.buffer.ByteBuf;

public class CorePacketOutLookAt implements PacketIO<PacketOutLookAt> {

	@Override
	public void read(PacketOutLookAt packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setAimWithEyes(readVarInt(in) == 1);
		packet.setX(in.readDouble());
		packet.setY(in.readDouble());
		packet.setZ(in.readDouble());
		boolean hasEntity = in.readBoolean();
		packet.setHasEntity(hasEntity);
		if (!hasEntity) 
			return;
		packet.setEntityID(readVarInt(in));
		packet.setAimAtEyes(readVarInt(in) == 1);
	}

	@Override
	public void write(PacketOutLookAt packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getAimAtEyes() ? 1 : 0, out);
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getY());
		out.writeDouble(packet.getZ());
		out.writeBoolean(packet.hasEntity());
		if (!packet.hasEntity()) 
			return;
		writeVarInt(packet.getEntityID(), out);
		writeVarInt(packet.getAimAtEyes() ? 1 : 0, out);
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
