package de.atlascore.inventory;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Merchant.MerchantRecipe;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.MerchantInventory;

public class CoreMerchantInventory extends CoreInventory implements MerchantInventory {

	private List<MerchantRecipe> recipes;
	private boolean canRestock, hideLevelProgress;
	private int level, xp;
	
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

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public int getExperience() {
		return xp;
	}

	@Override
	public void setExperience(int xp) {
		this.xp = xp;
	}

	@Override
	public boolean getHideLevelProgress() {
		return hideLevelProgress;
	}

	@Override
	public void setHideLevelProgress(boolean hide) {
		this.hideLevelProgress = hide;
	}

	@Override
	public boolean canRestock() {
		return canRestock;
	}

	@Override
	public void setCanRestock(boolean restock) {
		this.canRestock = restock;
	}

}
