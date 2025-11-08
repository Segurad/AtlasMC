package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.loot.LootTableHolder;

public interface ChestBoat extends Boat, InventoryHolder, LootTableHolder {
	
	public static final NBTCodec<ChestBoat>
	NBT_HANDLER = NBTCodec
					.builder(ChestBoat.class)
					.include(Boat.NBT_CODEC)
					.include(InventoryHolder.NBT_HANDLER)
					.include(LootTableHolder.NBT_CODEC)
					.build();

	@Override
	default NBTCodec<? extends ChestBoat> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
