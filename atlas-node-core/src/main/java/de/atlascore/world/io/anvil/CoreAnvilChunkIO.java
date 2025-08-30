package de.atlascore.world.io.anvil;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.world.Chunk;

/**
 * Class for handling the IO operations for a chunk column
 */
public class CoreAnvilChunkIO {
	

	public void loadChunk(Chunk chunk, NBTReader reader) throws IOException {
	
		chunk = null;
	}

	public boolean checkVersion(int version) {
		return true;
	}

}
