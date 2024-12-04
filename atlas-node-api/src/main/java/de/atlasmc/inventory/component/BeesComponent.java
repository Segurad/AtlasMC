package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.Bee;

public interface BeesComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:bees");
	
	BeesComponent clone();
	
	/**
	 * Returns a List containing all {@link Bee}s currently in this hive
	 * @return list of Bees
	 */
	List<Bee> getBees();
	
	void removeBee(Bee bee);
	
	void addBee(Bee bee);
	
	int getBeeCount();

}
