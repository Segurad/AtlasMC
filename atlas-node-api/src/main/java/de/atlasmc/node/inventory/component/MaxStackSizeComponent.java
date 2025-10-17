package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface MaxStackSizeComponent extends ItemComponent {

	public static final NBTCodec<MaxStackSizeComponent>
	NBT_HANDLER = NBTCodec
					.builder(MaxStackSizeComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.intField(ComponentType.MAX_STACK_SIZE.getNamespacedKey(), MaxStackSizeComponent::getMaxStackSize, MaxStackSizeComponent::setMaxStackSize, 0)
					.build();
	
	int getMaxStackSize();
	
	void setMaxStackSize(int stackSize);
	
	MaxStackSizeComponent clone();
	
	@Override
	default NBTCodec<? extends MaxStackSizeComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
