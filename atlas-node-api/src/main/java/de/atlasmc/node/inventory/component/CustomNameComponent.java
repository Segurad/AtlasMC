package de.atlasmc.node.inventory.component;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.Nameable;
import de.atlasmc.util.annotation.NotNull;

public interface CustomNameComponent extends ItemComponent, Nameable {
	
	@NotNull
	public static final NBTCodec<CustomNameComponent>
	NBT_CODEC = NBTCodec
					.builder(CustomNameComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.CUSTOM_NAME.getNamespacedKey(), CustomNameComponent::getCustomName, CustomNameComponent::setCustomName, Chat.NBT_CODEC)
					.build();
	
	@NotNull
	public static final StreamCodec<CustomNameComponent>
	STREAM_CODEC = StreamCodec
					.builder(CustomNameComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(CustomNameComponent::getCustomName, CustomNameComponent::setCustomName, Chat.STREAM_CODEC)
					.build();
	
	@Override
	CustomNameComponent clone();
	
	@Override
	default NBTCodec<? extends CustomNameComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends CustomNameComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
