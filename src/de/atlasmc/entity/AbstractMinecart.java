package de.atlasmc.entity;

import de.atlasmc.block.data.BlockData;

public interface AbstractMinecart extends Entity {
	
	public int getShakingPower();
	public int getShakingDirection();
	public float getShakingMultiplier();
	public BlockData getCustomBlock();
	public int getCustomBlockY();
	public boolean getShowCustomBlock();

}
