package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutEntityPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityPosition extends CoreAbstractHandler<PacketOutEntityPosition> {

	@Override
	public void read(PacketOutEntityPosition packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setDeltaX(in.readShort());
		packet.setDeltaY(in.readShort());
		packet.setDeltaZ(in.readShort());
		packet.setOnGround(in.readBoolean());
	}

	@Override
	public void write(PacketOutEntityPosition packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeShort(packet.getDeltaX());
		out.writeShort(packet.getDeltaY());
		out.writeShort(packet.getDeltaZ());
		out.writeBoolean(packet.isOnGround());
	}

	@Override
	public PacketOutEntityPosition createPacketData() {
		return new PacketOutEntityPosition();
	}

}
