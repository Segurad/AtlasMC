package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface ItemModelComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<ItemModelComponent>
	NBT_CODEC = CodecFieldCodec
				.builder(ItemModelComponent.class, NamespacedKey.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.getter(ItemModelComponent::getModel)
				.setter(ItemModelComponent::setModel)
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
