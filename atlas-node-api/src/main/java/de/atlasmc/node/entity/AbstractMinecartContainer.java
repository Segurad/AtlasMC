package de.atlasmc.node.entity;

import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.loot.LootTableHolder;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface AbstractMinecartContainer extends AbstractMinecart, InventoryHolder, LootTableHolder {

	public static final NBTCodec<AbstractMinecartContainer>
	NBT_HANDLER = NBTCodec
					.builder(AbstractMinecartContainer.class)
					.include(AbstractMinecart.NBT_HANDLER)
					.include(InventoryHolder.NBT_HANDLER)
					.include(LootTableHolder.NBT_HANDLER)
					.build();
	
	@Override
	default NBTCodec<? extends AbstractMinecartContainer> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
