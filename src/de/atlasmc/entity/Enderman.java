package de.atlasmc.entity;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;

public interface Enderman extends Monster {

	public Material getCarriedBlockType();
	
	public BlockData getCarriedBlock();
	
	public void setCarriedBlock(BlockData data);
	
	public void setCarriedBlockType(Material type);
	
	/**
	 * Sets the carried block as changed for the next update
	 */
	public void setCarriedBlockChanged();
	
	public boolean hasCarriedBlock();
	
	public void setScreaming(boolean screaming);
	
	public boolean isScreaming();
	
	public void setStaring(boolean staring);
	
	public boolean isStaring();
	
}
