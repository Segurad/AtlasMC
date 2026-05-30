package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.loot.LootTableHolder;
import de.atlasmc.util.AtlasUtil;
import de.atlasmc.util.annotation.NotNull;

public interface ContainerLootComponent extends ItemComponent, LootTableHolder {
	
	@NotNull
	public static final NBTCodec<ContainerLootComponent> 
	NBT_CODEC = NBTCodec
					.builder(ContainerLootComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.CONTAINER_LOOT.getNamespacedKey())
					.include(LootTableHolder.NBT_CODEC)
					.endComponent()
					.build();
	
	@NotNull
	public static final StreamCodec<ContainerLootComponent>
	STREAM_CODEC = StreamCodec
					.builder(ContainerLootComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(AtlasUtil.getSelf(), AtlasUtil.getSetVoid(), NBT_CODEC)
					.build();
	
	@Override
	ContainerLootComponent clone();

	@Override
	default NBTCodec<? extends ContainerLootComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends ContainerLootComponent> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
