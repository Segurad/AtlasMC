package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.FireworkExplosion;
import de.atlasmc.NamespacedKey;

public interface FireworksComponent extends ItemComponent {

	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:fireworks");
	
	List<FireworkExplosion> getExplosions();
	
	boolean hasExplosions();
	
	void addExplosion(FireworkExplosion explosion);
	
	void removeExplosions(FireworkExplosion explosion);
	
	int getFlightDuration();
	
	void setFlightDuration(int duration);
	
	FireworksComponent clone();
	
}
