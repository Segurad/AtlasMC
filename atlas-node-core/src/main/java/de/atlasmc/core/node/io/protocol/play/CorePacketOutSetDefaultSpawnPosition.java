package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetDefaultSpawnPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetDefaultSpawnPosition implements PacketIO<PacketOutSetDefaultSpawnPosition> {

	@Override
	public void read(PacketOutSetDefaultSpawnPosition packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.position = in.readLong();
		packet.angel = in.readFloat();
	}

	@Override
	public void write(PacketOutSetDefaultSpawnPosition packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.position);
		out.writeFloat(packet.angel);
	}

	@Override
	public PacketOutSetDefaultSpawnPosition createPacketData() {
		return new PacketOutSetDefaultSpawnPosition();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetDefaultSpawnPosition.class);
	}

}
