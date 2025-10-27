package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.util.ArrayList;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutChunkBiomes;
import de.atlasmc.node.io.protocol.play.PacketOutChunkBiomes.BiomeData;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChunkBiomes implements PacketIO<PacketOutChunkBiomes> {

	@Override
	public void read(PacketOutChunkBiomes packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final int count = readVarInt(in);
		if (count <= 0)
			return;
		packet.chunks = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			BiomeData data = new BiomeData();
			data.x = in.readInt();
			data.z = in.readInt();
			final int size = readVarInt(in);
			data.data = in.readBytes(size);
		}
	}

	@Override
	public void write(PacketOutChunkBiomes packet, ByteBuf out, ConnectionHandler con) throws IOException {
		if (packet.chunks == null || packet.chunks.isEmpty()) {
			writeVarInt(0, out);
			return;
		}
		writeVarInt(packet.chunks.size(), out);
		for (BiomeData data : packet.chunks) {
			out.writeInt(data.x);
			out.writeInt(data.z);
			writeVarInt(data.data.readableBytes(), out);
			out.writeBytes(data.data);
		}
	}

	@Override
	public PacketOutChunkBiomes createPacketData() {
		return new PacketOutChunkBiomes();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutChunkBiomes.class);
	}

}
