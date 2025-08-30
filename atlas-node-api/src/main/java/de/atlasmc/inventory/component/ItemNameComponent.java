package de.atlasmc.inventory.component;

import de.atlasmc.chat.Chat;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ItemNameComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<ItemNameComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ItemNameComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.chat(ComponentType.ITEM_NAME.getNamespacedKey(), ItemNameComponent::getName, ItemNameComponent::setName)
					.build();
	
	Chat getName();
	
	void setName(Chat name);
	
	ItemNameComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ItemNameComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
