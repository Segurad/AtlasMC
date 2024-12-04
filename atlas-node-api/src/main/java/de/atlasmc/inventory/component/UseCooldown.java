package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface UseCooldown extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:use_cooldown");
	
	float getSeconds();
	
	void setSeconds(float seconds);
	
	NamespacedKey getGroup();
	
	void setGroup(NamespacedKey group);
	
	UseCooldown clone();

}
