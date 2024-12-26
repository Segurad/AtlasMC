package de.atlasmc.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.util.palette.SingleValuePalette;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkSection;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

@DefaultPacketID(packetID = PacketPlay.OUT_CHUNK_BIOMES, definition = "chunks_biomes")
public class PacketOutChunkBiomes extends AbstractPacket implements PacketPlayOut {
	
	public List<BiomeData> chunks;
	
	public boolean hasChunks() {
		return chunks != null && !chunks.isEmpty();
	}
	
	public List<BiomeData> getChunks() {
		if (chunks == null)
			chunks = new ArrayList<>();
		return chunks;
	}
	
	public void addChunk(Chunk chunk) {
		if (chunk == null)
			throw new IllegalArgumentException("Chunk can not be null!");
		BiomeData data = new BiomeData();
		try {
			data.setChunk(chunk);
		} catch (IOException e) {
			throw new ProtocolException("Error while writing biome data!", e);
		}
		getChunks().add(data);
	}

	@Override
	public int getDefaultID() {
		return OUT_CHUNK_BIOMES;
	}
	
	public static class BiomeData {
		
		public int x;
		public int z;
		public ByteBuf data;
		
		public void setChunk(Chunk chunk) throws IOException {
			x = chunk.getX();
			z = chunk.getZ();
			data = Unpooled.buffer(calcDataSize(chunk));
			ChunkSection[] sections = chunk.getSections();
			for (int i = 0; i < sections.length; i++) {
				ChunkSection section = sections[i];
				if (section == null) {
					data.writeByte(0); // biomes palette bits
					writeVarInt(0, data); // biomes palette single value palette value
					writeVarInt(0, data); // biomes palette values length
					continue;
				}
				section.getBiomes().write(data);
			}
		}
		
		private int calcDataSize(Chunk chunk) {
			final ChunkSection[] sections = chunk.getSections();
			int size = 0;
			for (int i = 0; i < sections.length; i++) {
				ChunkSection section = sections[i];
				if (section == null) {
					size += SingleValuePalette.NULL_PALETTE_SERIALIZED_SIZE;
				} else {
					size += section.getBiomes().getSerializedSize();
				}
			}
			return size;
		}
		
	}

}
