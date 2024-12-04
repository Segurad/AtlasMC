package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.potion.PotionEffect;

public interface SuspiciousStewEffectsComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:suspicious_stew_effects");

	List<PotionEffect> getEffects();
	
	boolean hasEffects();
	
	void addEffects(PotionEffect effect);
	
	void removeEffects(PotionEffect effect);
	
	SuspiciousStewEffectsComponent clone();
	
}
