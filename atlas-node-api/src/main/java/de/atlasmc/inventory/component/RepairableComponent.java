package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.itempredicate.MaterialItemPredicate;
import de.atlasmc.inventory.itempredicate.MaterialTagItemPredicate;

public interface RepairableComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:repairable");
	
	MaterialItemPredicate getMaterials();
	
	void setMaterials(MaterialItemPredicate predicate);
	
	MaterialTagItemPredicate getMaterialTags();
	
	void setMaterialTags(MaterialTagItemPredicate predicate);
	
	RepairableComponent clone();

}
