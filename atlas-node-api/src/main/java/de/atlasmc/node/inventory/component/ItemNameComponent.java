package de.atlasmc.node.inventory.component;

import de.atlasmc.chat.Chat;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface ItemNameComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<ItemNameComponent>
	NBT_CODEC = NBTCodec
					.builder(ItemNameComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.ITEM_NAME.getNamespacedKey(), ItemNameComponent::getName, ItemNameComponent::setName, Chat.NBT_CODEC)
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
