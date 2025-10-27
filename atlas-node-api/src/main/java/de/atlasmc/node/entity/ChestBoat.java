package de.atlasmc.node.entity;

import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.loot.LootTableHolder;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ChestBoat extends Boat, InventoryHolder, LootTableHolder {
	
	public static final NBTCodec<ChestBoat>
	NBT_HANDLER = NBTCodec
					.builder(ChestBoat.class)
					.include(Boat.NBT_CODEC)
					.include(InventoryHolder.NBT_HANDLER)
					.include(LootTableHolder.NBT_HANDLER)
					.build();

	@Override
	default NBTCodec<? extends ChestBoat> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
