package de.atlasmc.entity;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;

public interface FallingBlock extends Entity {

	boolean canHurtEntities();
	
	BlockData getBlockData();
	
	boolean canDropItems();
	
	BlockType getBlockType();
	
	void setDropItems(boolean drop);
	
	void setHurtEntities(boolean hurtEntities);
	
	void setBlockData(BlockData data);
	
	void setBlockDataType(BlockType type);

	void setBaseDamage(float damage);
	
	float getBaseDamage();

	void setMaxDamage(int damage);
	
	/**
	 * Returns the max amount of damage this block can deal or -1 if no limit
	 * @return amount or -1
	 */
	int getMaxDamage();

	void setTileEntity(TileEntity tile);
	
	TileEntity getTileEntity();
	
}
