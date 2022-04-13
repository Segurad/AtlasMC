package de.atlasmc.inventory;

import java.util.List;

import de.atlasmc.entity.Merchant.MerchantRecipe;

public interface MerchantInventory extends Inventory {

	public MerchantRecipe getRecipe(int index);

	public int getRecipeCount();

	public List<MerchantRecipe> getRecipes();

	public void addRecipe(MerchantRecipe recipe);

}
