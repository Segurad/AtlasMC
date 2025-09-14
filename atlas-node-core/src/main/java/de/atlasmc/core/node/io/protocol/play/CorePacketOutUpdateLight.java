package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateLight;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateLight implements PacketIO<PacketOutUpdateLight> {
	
	@Override
	public void read(PacketOutUpdateLight packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.chunkX = readVarInt(in);
		packet.chunkZ = readVarInt(in);
		packet.skyMask = readBitSet(in);
		packet.blockMask = readBitSet(in);
		packet.emptySkyMask = readBitSet(in);
		packet.emptyBlockMask = readBitSet(in);
		// Read sky light data
		byte[][] skyLightSections = CorePacketOutChunkData.readLightData(in);
		packet.skyLight = skyLightSections;
		// Read block light data
		byte[][] blockLightSections = CorePacketOutChunkData.readLightData(in);
		packet.blockLight = blockLightSections;
	}

	@Override
	public void write(PacketOutUpdateLight packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.chunkX, out);
		writeVarInt(packet.chunkZ, out);
		writeBitSet(packet.skyMask, out);
		writeBitSet(packet.blockMask, out);
		writeBitSet(packet.emptySkyMask, out);
		writeBitSet(packet.emptyBlockMask, out);
		
		CorePacketOutChunkData.writeLightData(packet.skyLight, out);
		CorePacketOutChunkData.writeLightData(packet.blockLight, out);
	}
	
	@Override
	public PacketOutUpdateLight createPacketData() {
		return new PacketOutUpdateLight();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateLight.class);
	}

}
