package de.atlasmc.block.data;

import de.atlasmc.Material;

public interface BlockData extends Cloneable {

	public Material getType();
	public BlockData clone();
}
