package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.effect.ComponentEffect;

public interface DeathProtectionComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:death_protection");
	
	List<ComponentEffect> getEffects();
	
	boolean hasEffects();
	
	void addEffect(ComponentEffect effect);
	
	void removeEffect(ComponentEffect effect);

	DeathProtectionComponent clone();
	
}
