package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTNIOReader;
import de.atlasmc.nbt.io.NBTNIOWriter;
import de.atlasmc.node.io.protocol.play.PacketOutChunkData;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChunkData implements PacketIO<PacketOutChunkData> {

	@Override
	public void read(PacketOutChunkData packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		// position
		packet.chunkX = in.readInt();
		packet.chunkZ = in.readInt();
		// height map
		NBTNIOReader reader = new NBTNIOReader(in, true);
		reader.readNextEntry();
		final CharSequence name = reader.getFieldName();
		if (name.equals("MOTION_BLOCKING")) {
			packet.motionBlocking = reader.readLongArrayTag();
		} else reader.readLongArrayTag();
		if (reader.getType() != TagType.TAG_END) {
			if (name.equals("MOTION_BLOCKING")) {
				packet.motionBlocking = reader.readLongArrayTag();
			} else reader.readLongArrayTag();
		}
		reader.skipToEnd();
		reader.close();
		// section data
		packet.data = in.readBytes(readVarInt(in));
		// tiles
		packet.tileCount = readVarInt(in);
		packet.tiles = in.readBytes(in.readableBytes());
		// light
		packet.skyMask = readBitSet(in);
		packet.blockMask = readBitSet(in);
		packet.emptySkyMask = readBitSet(in);
		packet.emptyBlockMask = readBitSet(in);
		packet.skyLight = readLightData(in);
		packet.blockLight = readLightData(in);
	}

	@Override
	public void write(PacketOutChunkData packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		// position
		out.writeInt(packet.chunkX);
		out.writeInt(packet.chunkZ);
		// height map
		NBTNIOWriter writer = new NBTNIOWriter(out, true);
		writer.writeCompoundTag();
		writer.writeLongArrayTag("MOTION_BLOCKING", packet.motionBlocking);
		writer.writeEndTag();
		writer.close();
		// section data
		ByteBuf data = packet.data;
		writeVarInt(data.readableBytes(), out);
		out.writeBytes(data);
		// tiles
		writeVarInt(packet.tileCount, out);
		if (packet.tileCount != 0) {
			out.writeBytes(packet.tiles);
		}
		// write light
		writeBitSet(packet.skyMask, out);
		writeBitSet(packet.blockMask, out);
		writeBitSet(packet.emptySkyMask, out);
		writeBitSet(packet.emptyBlockMask, out);
		writeLightData(packet.skyLight, out);
		writeLightData(packet.blockLight, out);
	}
	
	public static void writeLightData(byte[][] light, ByteBuf out) {
		writeVarInt(light.length, out);
		for (byte[] lightSection : light) {
			writeVarInt(lightSection.length, out);
			out.writeBytes(lightSection);
		}
	}
	
	public static byte[][] readLightData(ByteBuf in) {
		final int length = readVarInt(in);
		byte[][] light = new byte[length][];
		for (int i = 0; i < length; i++) {
			int sectionLength = readVarInt(in);
			byte[] section = new byte[sectionLength];
			in.readBytes(section);
			light[i] = section;
		}
		return light;
	}

	@Override
	public PacketOutChunkData createPacketData() {
		return new PacketOutChunkData();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutChunkData.class);
	}
	
}
