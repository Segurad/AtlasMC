package de.atlasmc.core.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.inventory.AbstractHorseInventory;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.ItemStack;

public class CoreAbstractHorseInventory extends CoreInventory implements AbstractHorseInventory {

	public CoreAbstractHorseInventory(int size, InventoryType type, Chat title, InventoryHolder holder) {
		super(size, type, title, holder);
	}

	@Override
	public ItemStack getSaddle() {
		return getItem(0);
	}

	@Override
	public void setSaddle(ItemStack saddle) {
		setItem(0, saddle);
	}

}
