package de.atlasmc.inventory.component.effect;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.entity.Entity;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.NBTHolder;

public interface ComponentEffect extends NBTHolder, Namespaced {
	
	ConsumeEffectType getType();
	
	void apply(Entity target, ItemStack item);

}
