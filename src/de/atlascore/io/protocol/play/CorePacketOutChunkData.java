package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutChunkData;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkSection;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CorePacketOutChunkData extends AbstractPacket implements PacketOutChunkData {

	private int x, z, bitmask, tileCount;
	private boolean fullChunk;
	private long[] motionBlocking;
	private short[] biomes;
	private ByteBuf data, tiles;
	
	public CorePacketOutChunkData() {
		super(CoreProtocolAdapter.VERSION);
	}

	/**
	 * 
	 * @param x
	 * @param z
	 * @param chunk
	 * @param fullChunk
	 * @param bitmask will only be used in case of fullChunk is false
	 */
	public CorePacketOutChunkData(int x, int z, Chunk chunk, boolean fullChunk, int bitmask) {
		this();
		this.x = x;
		this.z = z;
		this.fullChunk = fullChunk;
		Set<ChunkSection> sections = chunk.getSections();
		if (fullChunk) {
			for (ChunkSection s : sections) {
				this.bitmask <<= 1;
				if (!s.isEmpty()) this.bitmask += 0x01;
			}
		} else this.bitmask = bitmask;
		motionBlocking = chunk.getHightMap();
		if (fullChunk) biomes = chunk.getBiomes();
		data = Unpooled.buffer();
		int i = 15;
		for (ChunkSection s : sections) {
			if (((this.bitmask >> i--) & 0x01) != 0x01) continue;
			data.writeShort(s.getBlockCount());
			List<BlockData> palette = s.getPalette();
			writeVarInt(palette.size(), data);
			for (BlockData bd : palette) {
				writeVarInt(bd.getStateID(), data);
			}
			long[] mapped = s.getCompactMappings();
			for (long l : mapped) {
				data.writeLong(l);
			}
		}
		List<TileEntity> tiles = chunk.getTileEntities();
		tileCount = tiles.size();
		if (tiles.isEmpty()) return;
		this.tiles = Unpooled.buffer();
		NBTNIOWriter writer = new NBTNIOWriter(this.tiles);
		try {
			for (TileEntity tile : tiles) {
				writer.writeCompoundTag(null);
				tile.toNBT(writer, false);
				writer.writeEndTag();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		x = in.readInt();
		z = in.readInt();
		fullChunk = in.readBoolean();
		bitmask = readVarInt(in);
		NBTNIOReader reader = new NBTNIOReader(in);
		reader.readNextEntry();
		String name = reader.getFieldName();
		if (name.equals("MOTION_BLOCKING")) {
			motionBlocking = reader.readLongArrayTag();
		} else reader.readLongArrayTag();
		if (reader.getType() != TagType.TAG_END) {
			if (name.equals("MOTION_BLOCKING")) {
				motionBlocking = reader.readLongArrayTag();
			} else reader.readLongArrayTag();
		}
		final int length = readVarInt(in);
		biomes = new short[length];
		for (int i = 0; i < length; i++) {
			biomes[i] = (short) readVarInt(in);
		}
		data = in.readBytes(readVarInt(in));
		tileCount = readVarInt(in);
		tiles = in.readBytes(in.readableBytes());
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(x);
		out.writeInt(z);
		out.writeBoolean(fullChunk);
		writeVarInt(bitmask, out);
		NBTNIOWriter writer = new NBTNIOWriter(out);
		writer.writeCompoundTag(null);
		writer.writeLongArrayTag("MOTION_BLOCKING", motionBlocking);
		writer.writeEndTag();
		writeVarInt(biomes.length, out);
		for (int i : biomes) {
			writeVarInt(i, out);
		}
		writeVarInt(data.readableBytes(), out);
		out.writeBytes(data);
		writeVarInt(tileCount, out);
		if (tileCount != 0) {
			out.writeBytes(tiles);
		}
	}
	
}
