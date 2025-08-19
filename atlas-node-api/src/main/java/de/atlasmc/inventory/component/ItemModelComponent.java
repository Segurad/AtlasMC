package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ItemModelComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<ItemModelComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ItemModelComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.namespacedKey(ComponentType.ITEM_MODEL, ItemModelComponent::getModel, ItemModelComponent::setModel)
					.build();
	
	NamespacedKey getModel();
	
	void setModel(NamespacedKey model);
	
	ItemModelComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ItemModelComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
