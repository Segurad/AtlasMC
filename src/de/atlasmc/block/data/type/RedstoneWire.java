package de.atlasmc.block.data.type;

import java.util.Set;

import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.AnaloguePowerable;

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
