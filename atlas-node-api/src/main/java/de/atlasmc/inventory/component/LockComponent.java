package de.atlasmc.inventory.component;

import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.itempredicate.ItemPredicate;

public interface LockComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:lock");
	
	int getMinCount();
	
	int getMaxCount();
	
	void setMinCount(int min);
	
	void setMaxCount(int max);
	
	Collection<ItemComponent> getComponents();
	
	boolean hasComponents();
	
	void addComponent(ItemComponent component);
	
	void removeComponent(ItemComponent component);
	
	Collection<ItemPredicate> getPredicates();
	
	boolean hasPredicates();
	
	void addPredicate(ItemPredicate predicate);
	
	void removePredicate(ItemPredicate predicate);
	
	LockComponent clone();

}
