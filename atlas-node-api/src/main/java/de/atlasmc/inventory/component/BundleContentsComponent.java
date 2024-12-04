package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemStack;

public interface BundleContentsComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:bundle_contents");
	
	List<ItemStack> getItems();
	
	boolean hasItems();
	
	void addItem(ItemStack item);
	
	void removeItem(ItemStack item);
	
	BundleContentsComponent clone();

}
