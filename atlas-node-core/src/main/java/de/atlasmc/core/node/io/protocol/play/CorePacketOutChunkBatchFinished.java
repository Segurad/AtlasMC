package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutChunkBatchFinished;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChunkBatchFinished implements PacketIO<PacketOutChunkBatchFinished> {

	@Override
	public void read(PacketOutChunkBatchFinished packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.chunks = readVarInt(in);
	}

	@Override
	public void write(PacketOutChunkBatchFinished packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.chunks, out);
	}

	@Override
	public PacketOutChunkBatchFinished createPacketData() {
		return new PacketOutChunkBatchFinished();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutChunkBatchFinished.class);
	}

}
