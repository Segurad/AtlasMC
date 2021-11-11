package de.atlasmc.world;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public interface ChunkLoader {
	
	public Chunk loadChunk(World world, NBTReader reader) throws IOException;
	
	public void saveChunk(Chunk chunk, NBTWriter writer) throws IOException;

}
