package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.ItemType;

public interface ContainerComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:container");
	
	List<ItemStack> getItems();
	
	boolean hasItems();
	
	void addItem(ItemStack item);
	
	void setItem(int slot, ItemStack item);
	
	void removeItem(ItemStack item);
	
	void removeItem(ItemType type);
	
	ContainerComponent clone();

}
