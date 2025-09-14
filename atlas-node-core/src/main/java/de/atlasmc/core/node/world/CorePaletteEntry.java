package de.atlasmc.core.node.world;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;

public class CorePaletteEntry {
	
	public BlockData data;
	public short count;
	
	public CorePaletteEntry(BlockData data) {
		this.data = data;
	}

	public BlockType getType() {
		return data.getType();
	}

}
