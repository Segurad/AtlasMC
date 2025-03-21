package de.atlascore.world;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;

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
