package de.atlasmc.recipe;

import java.util.List;

import de.atlasmc.inventory.ItemStack;

public class ItemIngredient implements Ingredient {

	private final List<ItemStack> items;
	private final boolean ignoreAmount, ignoreDamage;
	
	public ItemIngredient(List<ItemStack> items) {
		this(items, true, false);
	}
	
	public ItemIngredient(List<ItemStack> items, boolean ignoreAmount, boolean ignoreDamage) {
		if (items == null)
			throw new IllegalArgumentException("Items can not be null!");
		if (items.isEmpty())
			throw new IllegalArgumentException("Items can not be empty!");
		this.items = items;
		this.ignoreAmount = ignoreAmount;
		this.ignoreDamage = ignoreDamage;
	}
	
	@Override
	public List<ItemStack> getUseableItems() {
		return items;
	}

	@Override
	public ItemStack getItem() {
		return items.get(0);
	}

	@Override
	public boolean test(ItemStack item) {
		for (ItemStack i : items)
			if (i.isSimilar(item, ignoreAmount, ignoreDamage))
				return true;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ignoreAmount ? 1231 : 1237);
		result = prime * result + (ignoreDamage ? 1231 : 1237);
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemIngredient other = (ItemIngredient) obj;
		if (ignoreAmount != other.ignoreAmount)
			return false;
		if (ignoreDamage != other.ignoreDamage)
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

}
