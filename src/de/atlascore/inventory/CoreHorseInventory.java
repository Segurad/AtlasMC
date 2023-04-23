package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.ChestedHorse;
import de.atlasmc.inventory.HorseInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemStack;

public class CoreHorseInventory extends CoreAbstractHorseInventory implements HorseInventory {

	public CoreHorseInventory(Chat title, InventoryHolder holder) {
		super((holder instanceof ChestedHorse) && ((ChestedHorse) holder).hasChest() ? 17 : 2, InventoryType.HORSE, title, holder);
	}
	
	@Override
	public void setArmor(ItemStack armor) {
		setItem(1, armor);
	}

	@Override
	public ItemStack getArmor() {
		return getItem(1);
	}

}
