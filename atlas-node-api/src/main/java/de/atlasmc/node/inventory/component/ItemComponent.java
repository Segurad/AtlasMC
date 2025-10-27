package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ItemComponent extends NBTSerializable, StreamSerializable, Cloneable {
	
	public static final NBTCodec<ItemComponent> 
	NBT_CODEC = NBTCodec
					.builder(ItemComponent.class)
					.fieldKeyConstructor(ComponentType.REGISTRY_KEY, ComponentType::createItemComponent)
					.build();
	
	public static final StreamCodec<ItemComponent>
	STREAM_CODEC = StreamCodec
					.builder(ItemComponent.class)
					// TODO codec
					.build();
	
	/**
	 * Returns the type of this component
	 * @return type
	 */
	@NotNull
	ComponentType getType();
	
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
