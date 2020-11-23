package de.atlasmc.recipe;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.recipe.condition.Condition;

public abstract class AbstractRecipe implements Recipe {
	
	protected List<Condition> con;
	private boolean enabled;
	protected final ItemStack result;

	public AbstractRecipe(ItemStack result) {
		this.result = result;
		con = new ArrayList<Condition>(0);
	}
	
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
	
	@Override
	public ItemStack getResult() {
		return result.clone();
	}

}
