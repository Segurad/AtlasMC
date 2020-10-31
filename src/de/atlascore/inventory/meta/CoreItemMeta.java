package de.atlascore.inventory.meta;

import de.atlasmc.Material;
import de.atlasmc.inventory.meta.ItemMeta;

public class CoreItemMeta implements ItemMeta {

	private Material type;
	
	public CoreItemMeta(Material material) {
		this.type = material;
	}

	@Override
	public Material getType() {
		return type;
	}
}
