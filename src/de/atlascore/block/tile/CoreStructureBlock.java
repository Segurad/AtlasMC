package de.atlascore.block.tile;

import de.atlasmc.Material;
import de.atlasmc.block.tile.StructureBlock;
import de.atlasmc.world.Chunk;

public class CoreStructureBlock extends CoreTileEntity implements StructureBlock {

	// TODO implementation of StructureBlockTile
	
	public CoreStructureBlock(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
	}

}
