package de.atlasmc.core.node.world.io.anvil;

import java.io.IOException;

import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.node.world.Chunk;

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
