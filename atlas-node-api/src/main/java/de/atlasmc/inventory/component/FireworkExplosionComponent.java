package de.atlasmc.inventory.component;

import de.atlasmc.FireworkEffect;
import de.atlasmc.NamespacedKey;

public interface FireworkExplosionComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:firework_explosion");
	
	FireworkEffect getEffect();
	
	void setEffect(FireworkEffect effect);
	
	FireworkExplosionComponent clone();

}
