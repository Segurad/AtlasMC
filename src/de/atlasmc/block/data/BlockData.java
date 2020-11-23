package de.atlasmc.block.data;

import de.atlasmc.Material;

public interface BlockData extends Cloneable {

	public BlockData clone();
	public Material getMaterial();
	public boolean matches(BlockData data);
}
