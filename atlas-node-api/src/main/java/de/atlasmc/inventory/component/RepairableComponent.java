package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.itempredicate.ItemTypePredicate;
import de.atlasmc.inventory.itempredicate.TagItemPredicate;

public interface RepairableComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:repairable");
	
	ItemTypePredicate getMaterials();
	
	void setMaterials(ItemTypePredicate predicate);
	
	TagItemPredicate getMaterialTags();
	
	void setMaterialTags(TagItemPredicate predicate);
	
	RepairableComponent clone();

}
