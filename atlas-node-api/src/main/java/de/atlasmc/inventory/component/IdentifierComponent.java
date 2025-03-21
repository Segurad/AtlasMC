package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

/**
 * Component that stores a {@link NamespacedKey} to identify a item.
 */
public interface IdentifierComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("atlas:identifier");
	
	void setIdentifier(NamespacedKey id);
	
	NamespacedKey getIdentifier();

}
