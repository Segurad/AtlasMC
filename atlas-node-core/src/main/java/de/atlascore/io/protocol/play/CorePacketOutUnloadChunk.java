package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUnloadChunk;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUnloadChunk implements PacketIO<PacketOutUnloadChunk> {

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

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUnloadChunk.class);
	}

}
