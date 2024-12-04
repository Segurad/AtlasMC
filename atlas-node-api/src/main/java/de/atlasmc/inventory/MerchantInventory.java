package de.atlasmc.inventory;

import java.util.List;

import de.atlasmc.entity.Merchant.MerchantRecipe;

public interface MerchantInventory extends Inventory {

	MerchantRecipe getRecipe(int index);

	int getRecipeCount();

	List<MerchantRecipe> getRecipes();

	void addRecipe(MerchantRecipe recipe);

	int getLevel();
	
	void setLevel(int level);

	int getExperience();
	
	void setExperience(int xp);

	boolean getHideLevelProgress();
	
	void setHideLevelProgress(boolean hide);

	boolean canRestock();
	
	void setCanRestock(boolean restock);

}
