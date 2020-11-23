package de.atlasmc.recipe;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.recipe.condition.Condition;

public class StonecuttingRecipe extends AbstractRecipe implements Recipe {

	public StonecuttingRecipe(String key, ItemStack result, Material source) {
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
