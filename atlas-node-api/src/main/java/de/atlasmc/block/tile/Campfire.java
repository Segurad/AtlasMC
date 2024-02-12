package de.atlasmc.block.tile;

import de.atlasmc.inventory.ItemStack;

public interface Campfire extends TileEntity {
	
	public ItemStack[] getItems();
	
	public void setItems(ItemStack[] items);
	
	public ItemStack getItem(int index);
	
	public void setItem(int index, ItemStack item);
	
	public int[] getCookingTimes();
	
	public void setCookingTimes(int[] cookingTimes);
	
	public void setCookingTime(int index, int time);
	
	public int getCookingTime(int index);
	
	public int[] getTotalCookingTimes();
	
	public void setTotalCookingTimes(int[] totalCookingTimes);
	
	public void setTotalCookingTime(int index, int time);
	
	public int getTotalCookingTime(int index);

}
