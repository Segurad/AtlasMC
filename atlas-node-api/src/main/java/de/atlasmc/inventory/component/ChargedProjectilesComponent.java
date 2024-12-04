package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemStack;

public interface ChargedProjectilesComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:charged_projectiles");
	
	List<ItemStack> getProjectiles();
	
	boolean hasProjectiles();
	
	void addProjectile(ItemStack item);
	
	void removeProjectile(ItemStack item);
	
	ChargedProjectilesComponent clone();

}
