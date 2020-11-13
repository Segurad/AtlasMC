package de.atlasmc.inventory.meta;

import de.atlasmc.util.nbt.NBT;

public interface ItemMeta extends Cloneable {

	public ItemMeta clone();

	public NBT toNBT();
}
