package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.FireworkEffect;
import de.atlasmc.NamespacedKey;

public interface FireworksComponent extends ItemComponent {

	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:fireworks");
	
	List<FireworkEffect> getEffects();
	
	boolean hasEffects();
	
	void addEffect(FireworkEffect effect);
	
	void removeEffect(FireworkEffect effect);
	
	int getFlightDuration();
	
	void setFlightDuration(int duration);
	
	FireworksComponent clone();
	
}
