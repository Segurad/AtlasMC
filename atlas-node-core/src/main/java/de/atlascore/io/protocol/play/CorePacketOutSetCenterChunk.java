package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetCenterChunk;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetCenterChunk implements PacketIO<PacketOutSetCenterChunk> {

	@Override
	public void read(PacketOutSetCenterChunk packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.chunkX = readVarInt(in);
		packet.chunkZ = readVarInt(in);
	}

	@Override
	public void write(PacketOutSetCenterChunk packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.chunkX, out);
		writeVarInt(packet.chunkZ, out);
	}

	@Override
	public PacketOutSetCenterChunk createPacketData() {
		return new PacketOutSetCenterChunk();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetCenterChunk.class);
	}

}
