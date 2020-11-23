package de.atlasmc.recipe;

import java.util.List;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.recipe.condition.Condition;

public interface Recipe {

	public List<Condition> getConditions();
	public ItemStack getResult();
	public boolean isEnabled();
	public void setEnabled(boolean value);
}
