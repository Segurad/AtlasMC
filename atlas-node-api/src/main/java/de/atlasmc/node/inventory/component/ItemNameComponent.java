package de.atlasmc.node.inventory.component;

import de.atlasmc.chat.Chat;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ItemNameComponent extends ItemComponent {
	
	public static final NBTCodec<ItemNameComponent>
	NBT_HANDLER = NBTCodec
					.builder(ItemNameComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.chat(ComponentType.ITEM_NAME.getNamespacedKey(), ItemNameComponent::getName, ItemNameComponent::setName)
					.build();
	
	Chat getName();
	
	void setName(Chat name);
	
	ItemNameComponent clone();
	
	@Override
	default NBTCodec<? extends ItemNameComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
