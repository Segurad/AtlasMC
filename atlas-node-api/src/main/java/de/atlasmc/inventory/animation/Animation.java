package de.atlasmc.inventory.animation;

import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemStack;

public interface Animation {
	public void play(Inventory inv);

	public void setItems(ItemStack[] items);

	public Animation clone();
}
