package de.atlasmc.node.inventory;

import java.util.List;
import java.util.Objects;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface PocketHolder {
	
	@NotNull
	public static final NBTCodec<PocketHolder>
	NBT_CODEC = NBTCodec
					.builder(PocketHolder.class)
					.codecList("Inventory", PocketHolder::hasPocketItems, PocketHolder::getPocketItems, ItemStack.NBT_CODEC)
					.build();
	
	@NotNull
	List<ItemStack> getPocketItems();
	
	boolean hasPocketItems();
	
	default void addPocketItem(ItemStack item) {
		Objects.requireNonNull(item, "item");
		getPocketItems().add(item);
	}
	
	default void removePocketItem(ItemStack item) {
		if (item == null || !hasPocketItems())
			return;
		getPocketItems().remove(item);
	}

}
