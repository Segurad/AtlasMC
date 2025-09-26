package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutEntityEvent;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityEvent implements PacketIO<PacketOutEntityEvent> {

	@Override
	public void read(PacketOutEntityEvent packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = in.readInt();
		packet.status = in.readByte();
	}

	@Override
	public void write(PacketOutEntityEvent packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeInt(packet.entityID);
		out.writeByte(packet.status);
	}

	@Override
	public PacketOutEntityEvent createPacketData() {
		return new PacketOutEntityEvent();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutEntityEvent.class);
	}

}
