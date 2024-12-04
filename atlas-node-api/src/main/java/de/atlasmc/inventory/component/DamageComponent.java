package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface DamageComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:damage");
	
	int getDamage();
	
	void setDamage(int damage);
	
	DamageComponent clone();

}
