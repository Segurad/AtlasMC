package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface ItemModelComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:item_mode");
	
	NamespacedKey getModel();
	
	void setModel(NamespacedKey model);
	
	ItemModelComponent clone();

}
