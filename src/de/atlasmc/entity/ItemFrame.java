package de.atlasmc.entity;

import de.atlasmc.Rotation;
import de.atlasmc.block.BlockFace;
import de.atlasmc.inventory.ItemStack;

public interface ItemFrame {

	public ItemStack getItem();
	
	public void setItemStack(ItemStack item);
	
	public void setItemStack(ItemStack item, boolean playSound);
	
	public Rotation getRotation();
	
	public void setRotation(Rotation rotation);
	
	public void setFixed(boolean fixed);
	
	public boolean isFixed();
	
	public BlockFace getOrientation();
	
	public void setOrientation(BlockFace orientation);
	
}
