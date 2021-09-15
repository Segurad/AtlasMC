package de.atlascore.inventory;

import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.MerchantInventory;

public class CoreMerchantInventory extends CoreInventory implements MerchantInventory {

	public CoreMerchantInventory(String title, InventoryHolder holder) {
		super(3, InventoryType.MERCHANT, title, holder);
	}

}
