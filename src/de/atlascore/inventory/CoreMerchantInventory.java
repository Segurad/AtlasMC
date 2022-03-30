package de.atlascore.inventory;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Merchant.MerchantRecipe;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.MerchantInventory;

public class CoreMerchantInventory extends CoreInventory implements MerchantInventory {

	public CoreMerchantInventory(Chat title, InventoryHolder holder) {
		super(3, InventoryType.MERCHANT, title, holder);
	}

	@Override
	public MerchantRecipe getRecipe(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRecipeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MerchantRecipe> getRecipes() {
		// TODO Auto-generated method stub
		return null;
	}

}
