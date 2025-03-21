package de.atlasmc.inventory.component;

import de.atlasmc.FireworkExplosion;
import de.atlasmc.NamespacedKey;

public interface FireworkExplosionComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:firework_explosion");
	
	FireworkExplosion getExplosion();
	
	void setExplosion(FireworkExplosion effect);
	
	FireworkExplosionComponent clone();

}
