package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ItemModelComponent extends ItemComponent {
	
	public static final NBTCodec<ItemModelComponent>
	NBT_HANDLER = NBTCodec
					.builder(ItemModelComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.namespacedKey(ComponentType.ITEM_MODEL.getNamespacedKey(), ItemModelComponent::getModel, ItemModelComponent::setModel)
					.build();
	
	NamespacedKey getModel();
	
	void setModel(NamespacedKey model);
	
	ItemModelComponent clone();
	
	@Override
	default NBTCodec<? extends ItemModelComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
