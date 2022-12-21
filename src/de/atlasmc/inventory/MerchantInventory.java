package de.atlasmc.inventory;

import java.util.List;

import de.atlasmc.entity.Merchant.MerchantRecipe;

public interface MerchantInventory extends Inventory {

	public MerchantRecipe getRecipe(int index);

	public int getRecipeCount();

	public List<MerchantRecipe> getRecipes();

	public void addRecipe(MerchantRecipe recipe);

	public int getLevel();
	
	public void setLevel(int level);

	public int getExperience();
	
	public void setExperience(int xp);

	public boolean getHideLevelProgress();
	
	public void setHideLevelProgress(boolean hide);

	public boolean canRestock();
	
	public void setCanRestock(boolean restock);

}
