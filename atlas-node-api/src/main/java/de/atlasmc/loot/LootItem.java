package de.atlasmc.loot;

import java.util.Random;

import de.atlasmc.inventory.ItemStack;

public final class LootItem {

	private final int max;
	private final int min;
	private final ItemStack item;
	
	public LootItem(ItemStack item, int max, int min) {
		this.item = item;
		if (min > 0 && max > 0) {
			this.max = max-min;
			this.min = min;
		} else {
			this.max = 0; 
			this.min = item.getAmount();
		}
	}
	
	public final ItemStack getItemStack(final Random random) {
		ItemStack i = item.clone();
		i.setAmount(max > 0 ? random.nextInt(max)+min : min);
		return i;
	}

	public int getAmountMin() {
		return min;
	}
	
	public int getAmountMax() {
		return max;
	}
	
	public ItemStack getItem() {
		return item;
	}

}
