package de.atlascore.inventory.meta;

import de.atlasmc.Material;
import de.atlasmc.inventory.meta.SkullMeta;

public class CoreSkullMeta extends CoreTileEntityMeta implements SkullMeta {

	// TODO implement skullmeta
	
	public CoreSkullMeta(Material material) {
		super(material);
	}
	
	public CoreSkullMeta clone() {
		return (CoreSkullMeta) super.clone();
	}

}
