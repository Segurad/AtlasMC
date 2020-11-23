package de.atlasmc.recipe;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.recipe.condition.Condition;

public class BShapelessRecipe extends AbstractRecipe implements Recipe {

	public BShapelessRecipe(String key, ItemStack result) {
		super(result);
		con = new ArrayList<Condition>(0);
	}
	
	private List<Condition> con;
	private boolean enabled;

	@Override
	public List<Condition> getConditions() {
		return con;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean value) {
		enabled = value;
	}

}
