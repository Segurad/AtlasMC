package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.util.OpenCloneable;
import de.atlasmc.util.annotation.NotNull;

public interface ItemComponent extends NBTSerializable, StreamSerializable, OpenCloneable {
	
	public static final NBTCodec<ItemComponent> 
	NBT_CODEC = NBTCodec
					.builder(ItemComponent.class)
					.fieldKeyRegistryConstructor(ComponentType.REGISTRY_KEY, ComponentType::createItemComponent)
					.build();
	
	public static final StreamCodec<ItemComponent>
	STREAM_CODEC = StreamCodec
					.builder(ItemComponent.class)
					.registryVarIntConstructor(ComponentType.REGISTRY_KEY, ComponentType::createItemComponent, ItemComponent::getType)
					.build();
	
	/**
	 * Returns the type of this component
	 * @return type
	 */
	@NotNull
	ComponentType getType();
	
	@Override
	ItemComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends ItemComponent> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
