package de.atlasmc.entity;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;

public interface FallingBlock extends Entity {

	public boolean canHurtEntities();
	
	public BlockData getBlockData();
	
	public boolean canDropItems();
	
	public Material getMaterial();
	
	public void setDropItems(boolean drop);
	
	public void setHurtEntities(boolean hurtEntities);
	
	public void setBlockData(BlockData data);
	
	public void setBlockDataType(Material mat);

	public void setBaseDamage(float damage);
	
	public float getBaseDamage();

	public void setMaxDamage(int damage);
	
	/**
	 * Returns the max amount of damage this block can deal or -1 if no limit
	 * @return amount or -1
	 */
	public int getMaxDamage();

	public void setTileEntity(TileEntity tile);
	
	public TileEntity getTileEntity();
	
}
