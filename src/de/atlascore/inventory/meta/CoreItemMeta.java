package de.atlascore.inventory.meta;

import de.atlasmc.Material;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.nbt.NBT;

public class CoreItemMeta implements ItemMeta {
	
	public CoreItemMeta(Material material) {

	}
	
	public CoreItemMeta clone() {
		try {
			return (CoreItemMeta) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public NBT toNBT() {
		// TODO Auto-generated method stub
		return null;
	}
}
