package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutChunkBatchStart;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChunkBatchStart implements PacketIO<PacketOutChunkBatchStart> {

	@Override
	public void read(PacketOutChunkBatchStart packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public void write(PacketOutChunkBatchStart packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public PacketOutChunkBatchStart createPacketData() {
		return new PacketOutChunkBatchStart();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutChunkBatchStart.class);
	}

}
