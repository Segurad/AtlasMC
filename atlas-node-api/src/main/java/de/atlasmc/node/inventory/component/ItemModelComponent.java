package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface ItemModelComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<ItemModelComponent>
	NBT_CODEC = NBTCodec
					.builder(ItemModelComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.ITEM_MODEL.getNamespacedKey(), ItemModelComponent::getModel, ItemModelComponent::setModel, NamespacedKey.NBT_CODEC)
					.build();
	
	NamespacedKey getModel();
	
	void setModel(NamespacedKey model);
	
	@Override
	ItemModelComponent clone();
	
	@Override
	default NBTCodec<? extends ItemModelComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
