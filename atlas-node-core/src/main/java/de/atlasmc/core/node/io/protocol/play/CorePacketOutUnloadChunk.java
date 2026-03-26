package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutUnloadChunk;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUnloadChunk implements PacketCodec<PacketOutUnloadChunk> {

	@Override
	public void deserialize(PacketOutUnloadChunk packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.chunkX = in.readInt();
		packet.chunkZ = in.readInt();
	}

	@Override
	public void serialize(PacketOutUnloadChunk packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeInt(packet.chunkX);
		out.writeInt(packet.chunkZ);
	}

	@Override
	public PacketOutUnloadChunk createPacketData() {
		return new PacketOutUnloadChunk();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUnloadChunk.class);
	}

}
