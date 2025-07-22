package de.atlasmc.entity;

import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.loot.LootTableHolder;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AbstractMinecartContainer extends AbstractMinecart, InventoryHolder, LootTableHolder {

	public static final NBTSerializationHandler<AbstractMinecartContainer>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AbstractMinecartContainer.class)
					.include(AbstractMinecart.NBT_HANDLER)
					.include(InventoryHolder.NBT_HANDLER)
					.include(LootTableHolder.NBT_HANDLER)
					.build();
	
	@Override
	default NBTSerializationHandler<? extends AbstractMinecartContainer> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
