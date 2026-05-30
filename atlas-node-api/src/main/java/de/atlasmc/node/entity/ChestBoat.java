package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.loot.LootTableHolder;
import de.atlasmc.util.annotation.NotNull;

public interface ChestBoat extends Boat, InventoryHolder, LootTableHolder {
	
	@NotNull
	public static final NBTCodec<ChestBoat>
	NBT_CODEC = NBTCodec
					.builder(ChestBoat.class)
					.include(Boat.NBT_CODEC)
					.include(InventoryHolder.NBT_CODEC)
					.include(LootTableHolder.NBT_CODEC)
					.build();

	@Override
	default NBTCodec<? extends ChestBoat> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
