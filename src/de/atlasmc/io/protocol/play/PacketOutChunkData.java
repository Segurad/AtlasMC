package de.atlasmc.io.protocol.play;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.factory.ChunkFactory;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.util.VariableValueArray;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkSection;
import de.atlasmc.world.World;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

@DefaultPacketID(PacketPlay.OUT_CHUNK_DATA)
public class PacketOutChunkData extends AbstractPacket implements PacketPlayOut {
	
	private int x, z, bitmask, tileCount;
	private boolean fullChunk;
	private long[] motionBlocking;
	private short[] biomes;
	private ByteBuf data;
	private ByteBuf tiles;
	
	public int getX() {
		return x;
	}
	
	public int getZ() {
		return z;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	public int getBitmask() {
		return bitmask;
	}
	
	public void setBiomes(short[] biomes) {
		this.biomes = biomes;
	}
	
	public int getTileCount() {
		return tileCount;
	}
	
	public void setTileCount(int tileCount) {
		this.tileCount = tileCount;
	}
	
	public boolean isFullChunk() {
		return fullChunk;
	}
	
	public void setFullChunk(boolean fullChunk) {
		this.fullChunk = fullChunk;
	}
	
	public long[] getMotionBlocking() {
		return motionBlocking;
	}
	
	public void setMotionBlocking(long[] motionBlocking) {
		this.motionBlocking = motionBlocking;
	}
	
	public short[] getBiomes() {
		return biomes;
	}
	
	public void setBitmask(int bitmask) {
		this.bitmask = bitmask;
	}
	
	public ByteBuf getTiles() {
		return tiles;
	}
	
	public void setTiles(ByteBuf tiles) {
		this.tiles = tiles;
	}
	
	public ByteBuf getData() {
		return data;
	}
	
	public void setData(ByteBuf data) {
		this.data = data;
	}
	
	public void setChunk(Chunk chunk) {
		setChunk(chunk, 0, true);
	}
	
	public void setChunk(Chunk chunk, int mask) {
		setChunk(chunk, mask, false);
	}
	
	/**
	 * Extracts all needed data from a Chunk to send it.
	 * The given mask determines which sections will be send, ignored if full chunk.
	 * The least significant bit of the mask represents the lowest section.
	 * @param chunk
	 * @param mask of sections to send
	 */
	public void setChunk(Chunk chunk, int mask, boolean fullchunk) {
		x = chunk.getX();
		z = chunk.getZ();
		this.fullChunk = fullchunk;
		motionBlocking = chunk.getHightMap().array().clone();
		bitmask = setChunkSections(chunk, mask, fullchunk);
		tiles = Unpooled.buffer();
		NBTNIOWriter writer = new NBTNIOWriter(data);
		for (TileEntity entity : chunk.getTileEntities()) {
			try {
				writer.writeCompoundTag();
				entity.toNBT(writer, false);
				writer.writeEndTag();
			} catch (IOException e) {
				throw new NBTException("Error while writing TileEntity", e);
			}
		}
		writer.close();
		if (!fullchunk)
			return; // early return on non full chunk
		biomes = Arrays.copyOf(chunk.getBiomes(), 1024);
	}
	
	/**
	 * Constructs a new Chunk using the received data
	 * @param world used to construct the Chunk
	 * @return chunk
	 */
	public Chunk getChunk(World world) {
		Chunk chunk = ChunkFactory.DEFAULT_FACTORY.createChunk(world, x, z); 	
		if (motionBlocking != null)
			System.arraycopy(motionBlocking, 0, chunk.getHightMap().array(), 0, 256);
		getChunkSections(chunk, bitmask);
		if (tiles != null) {
			tiles.readerIndex(0);
			try {
				NBTNIOReader reader = new NBTNIOReader(tiles);
				for (int i = 0; i < tileCount; i++) {
					// TODO read tile
				}
				reader.close();
			} catch (IOException e) {
				throw new NBTException("Error while reading TileEntity", e);
			}
		}
		if (!fullChunk)
			return chunk; // early return on non full chunk
		System.arraycopy(biomes, 0, chunk.getBiomes(), 0, 1024);
		return chunk;			
	}
	
	@Override
	public int getDefaultID() {
		return OUT_CHUNK_DATA;
	}
	
	protected void getChunkSections(Chunk chunk, int mask) {
		data.readerIndex(0);
		for (int i = 0, m = mask; i < 16; i++, m>>=1) {
			if ((m & 0x1) != 0x1)
				continue;
			ChunkSection section = chunk.getSection(i);
			short blockCount = data.readShort();
			VariableValueArray indizes = section.getIndizes();
			indizes.resize(data.readUnsignedByte());
			int paletteSize = AbstractPacket.readVarInt(data);
			for (int j = 0; j < paletteSize; j++) {
				int stateID = AbstractPacket.readVarInt(data);
				BlockData data = null; // TODO BlockData by stateID
				section.setPaletteEntry(i, data, true);
			}
			final int longs = AbstractPacket.readVarInt(data);
			long[] backing = indizes.array();
			for (int j = 0; j < longs; j++)
				backing[j] = data.readLong();
		}
	}
	
	protected int setChunkSections(Chunk chunk, int mask, boolean fullchunk) {
		List<ChunkSection> sections = chunk.getSections();
		if (fullchunk) { // build mask
			mask = 0;
			for (int i = 15; i >= 0; i--) {
				mask <<= 1;
				if (sections.get(i) != null)
					mask |= 0x1;
			}
		}
		mask &= 0xFF;
		// set section data
		// calc required size
		int size = 0;
		for (int i = 0, m = mask; i < 16; i++, m>>=1) {
			if ((m & 0x1) != 0x1)
				continue;
			ChunkSection section = sections.get(i);
			size += 2; // BlockCount (short)
			size += 1; // bitsPerBlock (unsigned byte)
			int paletteSize = section.getPaletteSize();
			size += AbstractPacket.getVarIntLength(paletteSize);
			// only calculate with 3 bytes per palette entry
			// approximately 2 million block states possible
			size += 3 * paletteSize;
			// indizes (long array)
			size += section.getIndizesUnsafe().array().length * 8;
		}
		// write data
		data = Unpooled.buffer(size);
		for (int i = 0, m = mask; i < 16; i++, m>>=1) {
			if ((m & 0x1) != 0x1)
				continue;
			ChunkSection section = sections.get(i);
			data.writeShort(section.getBlockCount());
			VariableValueArray indizes = section.getIndizes();
			data.writeByte(indizes.getBitsPerValue());
			int paletteSize = section.getPaletteSize();
			AbstractPacket.writeVarInt(paletteSize, data);
			for (int j = 0; j < paletteSize; j++) {
				BlockData block = section.getPaletteEntry(j);
				AbstractPacket.writeVarInt(block.getStateID(), data);
			}
			AbstractPacket.writeVarInt(indizes.array().length, data);
			for (long l : indizes.array())
				data.writeLong(l);
		}
		return mask;
	}

}
