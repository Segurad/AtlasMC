package de.atlasmc.node.world;

import de.atlasmc.node.io.protocol.PlayerConnection;

public interface PlayerChunkListener extends ChunkViewer {
	
	public PlayerConnection getConnection();

}
