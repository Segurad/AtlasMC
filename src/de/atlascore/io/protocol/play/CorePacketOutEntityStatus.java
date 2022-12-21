package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutEntityStatus;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityStatus extends CoreAbstractHandler<PacketOutEntityStatus> {

	@Override
	public void read(PacketOutEntityStatus packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(in.readInt());
		packet.setStatus(in.readByte());
	}

	@Override
	public void write(PacketOutEntityStatus packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeInt(packet.getEntityID());
		out.writeByte(packet.getStatus());
	}

	@Override
	public PacketOutEntityStatus createPacketData() {
		return new PacketOutEntityStatus();
	}

}
