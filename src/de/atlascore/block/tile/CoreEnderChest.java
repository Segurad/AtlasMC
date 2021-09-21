package de.atlascore.block.tile;

import de.atlasmc.Material;
import de.atlasmc.block.tile.EnderChest;
import de.atlasmc.world.Chunk;

public class CoreEnderChest extends CoreTileEntity implements EnderChest {

	public CoreEnderChest(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
	}

}
