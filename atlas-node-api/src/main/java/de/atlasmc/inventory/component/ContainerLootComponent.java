package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ContainerLootComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:container_loot");
	
	public static final NBTSerializationHandler<ContainerLootComponent> 
	NBT_HANDLER = NBTSerializationHandler
					.builder(ContainerLootComponent.class)
					.beginComponent(COMPONENT_KEY.toString())
					.namespacedKey("loot_table", ContainerLootComponent::getLootTableKey, ContainerLootComponent::setLootTableKey)
					.longField("seed", ContainerLootComponent::getSeed, ContainerLootComponent::setSeed, 0)
					.endComponent()
					.build();
	
	NamespacedKey getLootTableKey();
	
	void setLootTableKey(NamespacedKey key);
	
	long getSeed();
	
	void setSeed(long seed);
	
	ContainerLootComponent clone();

	@Override
	default NBTSerializationHandler<ContainerLootComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
