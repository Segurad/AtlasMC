package de.atlasmc.node.inventory.component;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.factory.Factory;

@RegistryHolder(key="atlas:factory/item_component_factory")
public interface ItemComponentFactory extends Factory {

	/**
	 * 
	 * @param material
	 * @return a new ItemMeta
	 */
	ItemComponent createComponent(ComponentType type);
	
}
