package de.atlasmc.inventory.component;

import de.atlasmc.inventory.loot.LootTableHolder;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ContainerLootComponent extends ItemComponent, LootTableHolder {
	
	public static final NBTSerializationHandler<ContainerLootComponent> 
	NBT_HANDLER = NBTSerializationHandler
					.builder(ContainerLootComponent.class)
					.beginComponent(ComponentType.CONTAINER_LOOT)
					.include(LootTableHolder.NBT_HANDLER)
					.endComponent()
					.build();
	
	ContainerLootComponent clone();

	@Override
	default NBTSerializationHandler<ContainerLootComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
