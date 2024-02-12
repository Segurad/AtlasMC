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
	protected RecipeCategory category;
	
	public Recipe(NamespacedKey key, RecipeCategory category) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (category == null)
			throw new IllegalArgumentException("Category can not be null!");
		this.key = key;
		this.category = category;
	}
	
	public List<Condition> getConditions() {
		return con;
	}
	
	public RecipeCategory getCategory() {
		return category;
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

	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	public abstract RecipeType getType();

	public static Recipe getByName(String readStringTag) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
