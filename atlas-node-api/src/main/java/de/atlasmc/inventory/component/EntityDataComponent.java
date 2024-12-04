package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.Entity;

public interface EntityDataComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:entity_data");
	
	Entity getEntity();
	
	void setEntity(Entity entity);
	
	EntityDataComponent clone();

}
