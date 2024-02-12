package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutChunkData;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChunkData implements PacketIO<PacketOutChunkData> {

	@Override
	public void read(PacketOutChunkData packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		// position
		packet.x = in.readInt();
		packet.z = in.readInt();
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
		packet.skyLightMask = readBitSet(in);
		packet.blockLightMask = readBitSet(in);
		packet.emptySkyLightMask = readBitSet(in);
		packet.emptyBlockLightMask = readBitSet(in);
		packet.skyLight = readLightData(in);
		packet.blockLight = readLightData(in);
	}

	@Override
	public void write(PacketOutChunkData packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		// position
		out.writeInt(packet.x);
		out.writeInt(packet.z);
		// height map
		NBTNIOWriter writer = new NBTNIOWriter(out, true);
		writer.writeCompoundTag(null);
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
		writeBitSet(packet.skyLightMask, out);
		writeBitSet(packet.blockLightMask, out);
		writeBitSet(packet.emptySkyLightMask, out);
		writeBitSet(packet.emptyBlockLightMask, out);
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
