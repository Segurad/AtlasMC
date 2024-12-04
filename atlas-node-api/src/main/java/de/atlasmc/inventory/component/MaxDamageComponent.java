package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface MaxDamageComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:max_damage");
	
	int getMaxDamage();
	
	void setMaxDamage(int damage);
	
	MaxDamageComponent clone();

}
