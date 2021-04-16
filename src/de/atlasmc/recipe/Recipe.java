package de.atlasmc.recipe;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.recipe.condition.Condition;

public class Recipe {

	protected List<Condition> con;
	private boolean enabled;
	protected final ItemStack result;

	public Recipe(ItemStack result) {
		this.result = result;
		con = new ArrayList<Condition>(0);
	}
	
	public List<Condition> getConditions() {
		return con;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean value) {
		enabled = value;
	}
	
	public ItemStack getResult() {
		return result.clone();
	}
	
	public static Recipe getByName(String readStringTag) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNamespacedName() {
		// TODO Auto-generated method stub
		return null;
	}
}
