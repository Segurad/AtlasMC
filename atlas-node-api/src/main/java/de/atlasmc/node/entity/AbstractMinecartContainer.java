package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.loot.LootTableHolder;
import de.atlasmc.util.annotation.NotNull;

public interface AbstractMinecartContainer extends AbstractMinecart, InventoryHolder, LootTableHolder {

	@NotNull
	public static final NBTCodec<AbstractMinecartContainer>
	NBT_CODEC = NBTCodec
					.builder(AbstractMinecartContainer.class)
					.include(AbstractMinecart.NBT_CODEC)
					.include(InventoryHolder.NBT_CODEC)
					.include(LootTableHolder.NBT_CODEC)
					.build();
	
	@Override
	default NBTCodec<? extends AbstractMinecartContainer> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
