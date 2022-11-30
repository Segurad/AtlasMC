package de.atlasmc.recipe;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.recipe.condition.Condition;

public abstract class Recipe implements Namespaced {

	protected List<Condition> con;
	protected final NamespacedKey key;
	protected ItemStack result;
	protected ResultProcessor resultProcessor;

	public Recipe(NamespacedKey key) {
		this.key = key;
	}
	
	public List<Condition> getConditions() {
		return con;
	}
	
	public ItemStack getResult() {
		return result;
	}
	
	public void setResult(ItemStack result) {
		this.result = result;
	}
	
	public ResultProcessor getResultProcessor() {
		return resultProcessor;
	}
	
	public void setResultProcessor(ResultProcessor resultProcessor) {
		this.resultProcessor = resultProcessor;
	}
	
	public static Recipe getByName(String readStringTag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	public abstract RecipeType getType();
	
}
