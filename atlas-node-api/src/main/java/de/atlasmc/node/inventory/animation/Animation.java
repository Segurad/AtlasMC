package de.atlasmc.node.inventory.animation;

import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.ItemStack;

public interface Animation {
	public void play(Inventory inv);

	public void setItems(ItemStack[] items);

	public Animation clone();
}
