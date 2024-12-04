package de.atlasmc.inventory.itempredicate;

import java.util.HashSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.inventory.ItemStack;

public class MaterialItemPredicate implements ItemPredicate {
	
	private Set<Material> materials;
	
	public MaterialItemPredicate() {
		materials = new HashSet<>();
	}

	@Override
	public boolean match(ItemStack item) {
		return materials.contains(item.getType());
	}

	@Override
	public boolean match(Material material) {
		return materials.contains(material);
	}

}
