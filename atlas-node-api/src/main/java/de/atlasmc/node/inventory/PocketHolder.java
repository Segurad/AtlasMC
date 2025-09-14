package de.atlasmc.node.inventory;

import java.util.List;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface PocketHolder {
	
	public static final NBTSerializationHandler<PocketHolder>
	NBT_HANDLER = NBTSerializationHandler
					.builder(PocketHolder.class)
					.typeList("Inventory", PocketHolder::hasPocketItems, PocketHolder::getPocketItems, ItemStack.NBT_HANDLER)
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
