package de.atlasmc.node.inventory.component;

import de.atlasmc.node.inventory.loot.LootTableHolder;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ContainerLootComponent extends ItemComponent, LootTableHolder {
	
	public static final NBTCodec<ContainerLootComponent> 
	NBT_HANDLER = NBTCodec
					.builder(ContainerLootComponent.class)
					.beginComponent(ComponentType.CONTAINER_LOOT.getNamespacedKey())
					.include(LootTableHolder.NBT_HANDLER)
					.endComponent()
					.build();
	
	ContainerLootComponent clone();

	@Override
	default NBTCodec<ContainerLootComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
