package de.atlasmc.core.node.block.tile;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.Campfire;
import de.atlasmc.node.inventory.ItemStack;

public class CoreCampfire extends CoreTileEntity implements Campfire {
	
	private final ItemStack[] items;
	private final int[] cookingTimes;
	private final int[] cookingTotalTimes;

	public CoreCampfire(BlockType type) {
		super(type);
		cookingTimes = new int[4];
		cookingTotalTimes = new int[4];
		items = new ItemStack[4];
	}

	@Override
	public ItemStack[] getItems() {
		return items;
	}

	@Override
	public ItemStack getItem(int index) {
		return items[index];
	}

	@Override
	public void setItem(int index, ItemStack item) {
		this.items[index] = item;
	}

	@Override
	public int[] getCookingTimes() {
		return cookingTimes;
	}

	@Override
	public void setCookingTime(int index, int time) {
		this.cookingTimes[index] = time;
	}

	@Override
	public int getCookingTime(int index) {
		return cookingTimes[index];
	}

	@Override
	public int[] getTotalCookingTimes() {
		return cookingTotalTimes;
	}

	@Override
	public void setTotalCookingTime(int index, int time) {
		this.cookingTotalTimes[index] = time;
	}

	@Override
	public int getTotalCookingTime(int index) {
		return cookingTotalTimes[index];
	}

	@Override
	public void setItems(ItemStack[] items) {
		System.arraycopy(items, 0, this.items, 0, 4);
	}

	@Override
	public void setCookingTimes(int[] cookingTimes) {
		System.arraycopy(cookingTimes, 0, this.cookingTimes, 0, 4);
	}

	@Override
	public void setTotalCookingTimes(int[] totalCookingTimes) {
		System.arraycopy(totalCookingTimes, 0, this.cookingTotalTimes, 0, 4);
	}

}
