package de.atlasmc.entity;

import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.loot.LootTableHolder;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ChestBoat extends Boat, InventoryHolder, LootTableHolder {
	
	public static final NBTSerializationHandler<ChestBoat>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ChestBoat.class)
					.include(Boat.NBT_HANDLER)
					.include(InventoryHolder.NBT_HANDLER)
					.include(LootTableHolder.NBT_HANDLER)
					.build();

	@Override
	default NBTSerializationHandler<? extends ChestBoat> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
