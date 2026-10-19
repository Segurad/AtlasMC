package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.annotation.NotNull;

public interface UseRemainderComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<UseRemainderComponent>
	NBT_CODEC = CodecFieldCodec
					.builder(UseRemainderComponent.class, ItemStack.NBT_CODEC)
					.include(ItemComponent.NBT_CODEC)
					.getter(UseRemainderComponent::getItem)
					.setter(UseRemainderComponent::setItem)
					.build();
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	@Override
	UseRemainderComponent clone();
	
	@Override
	default NBTCodec<? extends UseRemainderComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
