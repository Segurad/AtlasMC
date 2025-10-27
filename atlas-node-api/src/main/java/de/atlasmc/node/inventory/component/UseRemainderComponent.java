package de.atlasmc.node.inventory.component;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface UseRemainderComponent extends ItemComponent {
	
	public static final NBTCodec<UseRemainderComponent>
	NBT_HANDLER = NBTCodec
					.builder(UseRemainderComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.typeCompoundField(ComponentType.USE_REMAINDER.getNamespacedKey(), UseRemainderComponent::getItem, UseRemainderComponent::setItem, ItemStack.NBT_HANDLER)
					.build();
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	UseRemainderComponent clone();
	
	@Override
	default NBTCodec<? extends UseRemainderComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
