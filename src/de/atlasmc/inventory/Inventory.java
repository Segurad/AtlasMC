package de.atlasmc.inventory;

import java.util.List;

import de.atlasmc.entity.HumanEntity;

public interface Inventory {

	public int getSize();

	public void setItem(int slot, ItemStack icon);

	public ItemStack getItem(int slot);

	public List<HumanEntity> getViewers();

}
