package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInChunkBatchReceived;
import io.netty.buffer.ByteBuf;

public class CorePacketInChunkBatchReceived implements PacketCodec<PacketInChunkBatchReceived> {

	@Override
	public void deserialize(PacketInChunkBatchReceived packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.chunksPerTick = in.readFloat();
	}

	@Override
	public void serialize(PacketInChunkBatchReceived packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
