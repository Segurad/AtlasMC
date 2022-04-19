package de.atlasmc.entity;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;

public interface AbstractMinecart extends Entity {
	
	public int getShakingPower();
	
	public void setShakingPower(int power);
	
	public int getShakingDirection();
	
	public void setShakingDirection(int direction);
	
	public float getShakingMultiplier();
	
	public void setShakingMultiplier(float multiplier);
	
	public BlockData getCustomBlock();
	
	public boolean hasCustomBlock();
	
	public void setCustomBlock(BlockData data);
	
	public void setCustomBlockType(Material material);
	
	public int getCustomBlockY();
	
	public void setCustomBlockY(int y);
	
	public boolean getShowCustomBlock();

	public void setShowCustomBlock(boolean show);
	
}
