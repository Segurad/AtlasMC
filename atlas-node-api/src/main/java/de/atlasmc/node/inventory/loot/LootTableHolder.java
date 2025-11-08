package de.atlasmc.node.inventory.loot;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.NBTCodec;

public interface LootTableHolder {
	
	public static final NBTCodec<LootTableHolder>
	NBT_CODEC = NBTCodec
					.builder(LootTableHolder.class)
					.codec("LootTable", LootTableHolder::getLootTable, LootTableHolder::setLootTable, NamespacedKey.NBT_CODEC)
					.longField("LootTableSeed", LootTableHolder::getLootTableSeed, LootTableHolder::setLootTableSeed, 0)
					.build();
	
	NamespacedKey getLootTable();
	
	void setLootTable(NamespacedKey key);
	
	long getLootTableSeed();
	
	void setLootTableSeed(long seed);

}
