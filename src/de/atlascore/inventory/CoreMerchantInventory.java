package de.atlascore.inventory;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Merchant.MerchantRecipe;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.MerchantInventory;

public class CoreMerchantInventory extends CoreInventory implements MerchantInventory {

	private List<MerchantRecipe> recipes;
	
	public CoreMerchantInventory(Chat title, InventoryHolder holder) {
		super(3, InventoryType.MERCHANT, title, holder);
	}

	@Override
	public MerchantRecipe getRecipe(int index) {
		if (index >= getRecipeCount() || index < 0)
			return null;
		return recipes.get(index);
	}

	@Override
	public int getRecipeCount() {
		return recipes != null ? recipes.size() : 0;
	}

	@Override
	public List<MerchantRecipe> getRecipes() {
		if (recipes == null)
			recipes = new ArrayList<>();
		return recipes;
	}

	@Override
	public void addRecipe(MerchantRecipe recipe) {
		if (recipe == null)
			throw new IllegalArgumentException("Recipe can not be null!");
		getRecipes().add(recipe);
	}

}
