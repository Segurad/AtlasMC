package de.atlasmc.io.protocol.play;

import static de.atlasmc.io.PacketUtil.getVarIntLength;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketChunkLight;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkSection;
import de.atlasmc.world.World;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

@DefaultPacketID(packetID = PacketPlay.OUT_CHUNK_DATA_AND_UPDATE_LIGHT, definition = "level_chunk_with_light")
public class PacketOutChunkData extends AbstractPacketChunkLight implements PacketPlayOut {
	
	public long[] motionBlocking;
	
	public ByteBuf data;
	
	public int tileCount;
	public ByteBuf tiles;
	
	/**
	 * Extracts all needed data from a Chunk to send it.
	 * @param chunk
	 * @throws IOException 
	 */
	public void setChunk(Chunk chunk) throws IOException {
		chunkX = chunk.getX();
		chunkZ = chunk.getZ();
		motionBlocking = chunk.getHightMap().array().clone();
		data = Unpooled.buffer(calcChunkDataSize(chunk));
		ChunkSection[] sections = chunk.getSections();
		for (int i = 0; i < sections.length; i++) {
			ChunkSection section = sections[i];
			if (section == null) {
				writeVarInt(0, data); // non air blocks
				data.writeByte(0); // block palette bits
				writeVarInt(0, data); // block palette single value palette value
				writeVarInt(0, data); // block palette values length
				data.writeByte(0); // biomes palette bits
				writeVarInt(0, data); // biomes palette single value palette value
				writeVarInt(0, data); // biomes palette values length
			} else {
				writeVarInt(section.getBlockCount(), data);
				section.getBlockData().write(data);
				section.getBiomes().write(data);
			}
		}
		tiles = Unpooled.buffer();
		NBTNIOWriter writer = new NBTNIOWriter(data, true);
		for (TileEntity entity : chunk.getTileEntities()) {
			int packedXZ = ((entity.getX() & 0xF) << 4) | (entity.getZ() & 0xF);
			tiles.writeByte(packedXZ);
			short y = (short) entity.getY();
			tiles.writeShort(y);
			writeVarInt(entity.getID(), data);
			try {
				writer.writeCompoundTag();
				entity.toNBT(writer, false);
				writer.writeEndTag();
			} catch (IOException e) {
				throw new NBTException("Error while writing TileEntity", e);
			}
		}
		writer.close();
	}
	
	public static int calcChunkDataSize(Chunk chunk) {
		final ChunkSection[] sections = chunk.getSections();
		int size = 0;
		for (int i = 0; i < sections.length; i++) {
			ChunkSection section = sections[i];
			size += 2; // block count short
			if (section == null) {
				size += 2; // palette bits per block * 2
				size += getVarIntLength(0) * 4; // single value pallete 2 + empty long array 2
			} else {
				size += section.getBlockData().getSerializedSize();
				size += section.getBiomes().getSerializedSize();
			}
		}
		return size;
	}
	
	/**
	 * Constructs a new Chunk using the received data
	 * @param world used to construct the Chunk
	 * @return chunk
	 */
	public Chunk getChunk(World world) {
		return null;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_CHUNK_DATA_AND_UPDATE_LIGHT;
	}

}
