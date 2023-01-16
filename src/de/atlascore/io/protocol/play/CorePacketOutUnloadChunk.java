package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUnloadChunk;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUnloadChunk extends PacketIO<PacketOutUnloadChunk> {

	@Override
	public void read(PacketOutUnloadChunk packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setChunkX(in.readInt());
		packet.setChunkZ(in.readInt());
	}

	@Override
	public void write(PacketOutUnloadChunk packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeInt(packet.getChunkX());
		out.writeInt(packet.getChunkZ());
	}

	@Override
	public PacketOutUnloadChunk createPacketData() {
		return new PacketOutUnloadChunk();
	}

}
