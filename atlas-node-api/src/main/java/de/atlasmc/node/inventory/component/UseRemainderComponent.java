package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemStack;

public interface UseRemainderComponent extends ItemComponent {
	
	public static final NBTCodec<UseRemainderComponent>
	NBT_HANDLER = NBTCodec
					.builder(UseRemainderComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.USE_REMAINDER.getNamespacedKey(), UseRemainderComponent::getItem, UseRemainderComponent::setItem, ItemStack.NBT_CODEC)
					.build();
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	UseRemainderComponent clone();
	
	@Override
	default NBTCodec<? extends UseRemainderComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
