package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketInChunkBatchReceived;
import io.netty.buffer.ByteBuf;

public class CorePacketInChunkBatchReceived implements PacketIO<PacketInChunkBatchReceived> {

	@Override
	public void read(PacketInChunkBatchReceived packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.chunksPerTick = in.readFloat();
	}

	@Override
	public void write(PacketInChunkBatchReceived packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeFloat(packet.chunksPerTick);
	}

	@Override
	public PacketInChunkBatchReceived createPacketData() {
		return new PacketInChunkBatchReceived();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInChunkBatchReceived.class);
	}

}
