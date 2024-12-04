package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface CanBreakComponent extends AbstractBlockPredicateComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:can_break");
	
	CanBreakComponent clone();

}
