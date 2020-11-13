package de.atlasmc.entity;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;

public interface FallingBlock extends Entity {

	public boolean canHurtEntities();
	public BlockData getBlockData();
	public boolean getDropItems();
	public Material getMaterial();
	public void setDropItems(boolean drop);
	public void setHurtEntities(boolean hurtEntities);
	
}
