package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutSpawnPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSpawnPosition extends CoreAbstractHandler<PacketOutSpawnPosition> {

	@Override
	public void read(PacketOutSpawnPosition packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
	}

	@Override
	public void write(PacketOutSpawnPosition packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
	}

	@Override
	public PacketOutSpawnPosition createPacketData() {
		return new PacketOutSpawnPosition();
	}

}
