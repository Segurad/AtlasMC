package de.atlasmc.inventory.component;

import java.util.Collection;

import de.atlasmc.Material;
import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemStack;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public interface ContainerComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:container");
	
	Int2ObjectMap<ItemStack> getSlots();
	
	Collection<ItemStack> getItems();
	
	boolean hasItems();
	
	void setItem(int slot, ItemStack item);
	
	void removeItem(ItemStack item);
	
	void removeItem(Material material);
	
	ContainerComponent clone();

}
