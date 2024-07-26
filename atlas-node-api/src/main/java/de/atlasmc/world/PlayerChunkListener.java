package de.atlasmc.world;

import de.atlasmc.io.protocol.PlayerConnection;

public interface PlayerChunkListener extends ChunkViewer {
	
	public PlayerConnection getConnection();

}
