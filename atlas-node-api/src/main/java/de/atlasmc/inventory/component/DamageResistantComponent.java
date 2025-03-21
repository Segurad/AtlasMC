package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.DamageType;
import de.atlasmc.tag.Tag;

public interface DamageResistantComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:damage_resistant");
	
	Tag<DamageType> getDamageTypes();
	
	void setDamageTypes(Tag<DamageType> types);
	
	DamageResistantComponent clone();

}
