package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutChunkData;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChunkData extends PacketIO<PacketOutChunkData> {

	@Override
	public void read(PacketOutChunkData packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setX(in.readInt());
		packet.setZ(in.readInt());
		packet.setFullChunk(in.readBoolean());
		packet.setBitmask(readVarInt(in));
		NBTNIOReader reader = new NBTNIOReader(in);
		reader.readNextEntry();
		final CharSequence name = reader.getFieldName();
		if (name.equals("MOTION_BLOCKING")) {
			packet.setMotionBlocking(reader.readLongArrayTag());
		} else reader.readLongArrayTag();
		if (reader.getType() != TagType.TAG_END) {
			if (name.equals("MOTION_BLOCKING")) {
				packet.setMotionBlocking(reader.readLongArrayTag());
			} else reader.readLongArrayTag();
		}
		reader.skipToEnd();
		reader.close();
		final int length = readVarInt(in);
		short[] biomes = new short[length];
		for (int i = 0; i < length; i++) {
			biomes[i] = (short) readVarInt(in);
		}
		packet.setBiomes(biomes);
		packet.setData(in.readBytes(readVarInt(in)));
		packet.setTileCount(readVarInt(in));
		packet.setTiles(in.readBytes(in.readableBytes()));
	}

	@Override
	public void write(PacketOutChunkData packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeInt(packet.getX());
		out.writeInt(packet.getZ());
		out.writeBoolean(packet.isFullChunk());
		writeVarInt(packet.getBitmask(), out);
		NBTNIOWriter writer = new NBTNIOWriter(out);
		writer.writeCompoundTag(null);
		writer.writeLongArrayTag("MOTION_BLOCKING", packet.getMotionBlocking());
		writer.writeEndTag();
		writer.close();
		short[] biomes = packet.getBiomes();
		writeVarInt(biomes.length, out);
		for (int i : biomes) {
			writeVarInt(i, out);
		}
		ByteBuf data = packet.getData();
		writeVarInt(data.readableBytes(), out);
		out.writeBytes(data);
		writeVarInt(packet.getTileCount(), out);
		if (packet.getTileCount() != 0) {
			out.writeBytes(packet.getTiles());
		}
	}
	
	@Override
	public PacketOutChunkData createPacketData() {
		return new PacketOutChunkData();
	}
	
}
