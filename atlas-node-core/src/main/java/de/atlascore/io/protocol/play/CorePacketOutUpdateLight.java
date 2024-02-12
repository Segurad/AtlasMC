package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateLight;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateLight implements PacketIO<PacketOutUpdateLight> {
	
	@Override
	public void read(PacketOutUpdateLight packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setChunkX(readVarInt(in));
		packet.setChunkZ(readVarInt(in));
		packet.setSkyMask(readBitSet(in));
		packet.setBlockMask(readBitSet(in));
		packet.setEmptySkyMask(readBitSet(in));
		packet.setEmptyBlockMask(readBitSet(in));
		// Read sky light data
		byte[][] skyLightSections = CorePacketOutChunkData.readLightData(in);
		packet.setSkyLight(skyLightSections);
		// Read block light data
		byte[][] blockLightSections = CorePacketOutChunkData.readLightData(in);
		packet.setBlockLight(blockLightSections);
	}

	@Override
	public void write(PacketOutUpdateLight packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getChunkX(), out);
		writeVarInt(packet.getChunkZ(), out);
		writeBitSet(packet.getSkyMask(), out);
		writeBitSet(packet.getBlockMask(), out);
		writeBitSet(packet.getEmptySkyMask(), out);
		writeBitSet(packet.getEmptyBlockMask(), out);
		
		CorePacketOutChunkData.writeLightData(packet.getSkyLight(), out);
		CorePacketOutChunkData.writeLightData(packet.getBlockLight(), out);
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
