package de.atlasmc.node.inventory.loot;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface LootTableHolder {
	
	public static final NBTCodec<LootTableHolder>
	NBT_HANDLER = NBTCodec
					.builder(LootTableHolder.class)
					.namespacedKey("LootTable", LootTableHolder::getLootTable, LootTableHolder::setLootTable)
					.longField("LootTableSeed", LootTableHolder::getLootTableSeed, LootTableHolder::setLootTableSeed, 0)
					.build();
	
	NamespacedKey getLootTable();
	
	void setLootTable(NamespacedKey key);
	
	long getLootTableSeed();
	
	void setLootTableSeed(long seed);

}
