package de.atlasmc.node.inventory;

import java.util.List;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface PocketHolder {
	
	public static final NBTCodec<PocketHolder>
	NBT_HANDLER = NBTCodec
					.builder(PocketHolder.class)
					.codecList("Inventory", PocketHolder::hasPocketItems, PocketHolder::getPocketItems, ItemStack.NBT_HANDLER)
					.build();
	
	@NotNull
	List<ItemStack> getPocketItems();
	
	boolean hasPocketItems();
	
	default void addPocketItem(ItemStack item) {
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		getPocketItems().add(item);
	}
	
	default void removePocketItem(ItemStack item) {
		if (item == null)
			return;
		if (!hasPocketItems())
			return;
		getPocketItems().remove(item);
	}

}
