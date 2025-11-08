package de.atlasmc.node.inventory.component;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.Nameable;

public interface CustomNameComponent extends ItemComponent, Nameable {
	
	public static final NBTCodec<CustomNameComponent>
	NBT_HANDLER = NBTCodec
					.builder(CustomNameComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.CUSTOM_NAME.getNamespacedKey(), CustomNameComponent::getCustomName, CustomNameComponent::setCustomName, Chat.NBT_CODEC)
					.build();
	
	public static final StreamCodec<CustomNameComponent>
	STREAM_CODEC = StreamCodec
					.builder(CustomNameComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(CustomNameComponent::getCustomName, CustomNameComponent::setCustomName, Chat.STREAM_CODEC)
					.build();
	
	CustomNameComponent clone();
	
	@Override
	default NBTCodec<? extends CustomNameComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	@Override
	default StreamCodec<? extends CustomNameComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
