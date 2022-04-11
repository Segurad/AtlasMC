package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.AbstractHorseInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;

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
