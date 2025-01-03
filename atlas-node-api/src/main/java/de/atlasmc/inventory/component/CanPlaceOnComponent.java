package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface CanPlaceOnComponent extends AbstractBlockPredicateComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:can_place_on");

	CanPlaceOnComponent clone();
	
}
