package de.atlasmc.node.inventory.component;

import de.atlasmc.chat.Chat;
import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface ItemNameComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<ItemNameComponent>
	NBT_CODEC = CodecFieldCodec
				.builder(ItemNameComponent.class, Chat.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.getter(ItemNameComponent::getName)
				.setter(ItemNameComponent::setName)
				.build();
	
	Chat getName();
	
	void setName(Chat name);
	
	@Override
	ItemNameComponent clone();
	
	@Override
	default NBTCodec<? extends ItemNameComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
