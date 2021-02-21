package de.atlasmc.block.data;

import java.util.Set;

import de.atlasmc.block.BlockFace;

public interface RedstoneWire extends AnaloguePowerable {
	
	public Set<BlockFace> getAllowedFaces();
	public Connection getFace(BlockFace face);
	public void setFace(BlockFace face, Connection connection);
	
	public static enum Connection {
		UP,
		SIDE,
		NONE
	}

}
