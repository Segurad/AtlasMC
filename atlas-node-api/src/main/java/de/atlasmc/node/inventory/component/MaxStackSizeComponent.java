package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface MaxStackSizeComponent extends ItemComponent {

	@NotNull
	public static final NBTCodec<MaxStackSizeComponent>
	NBT_CODEC = NBTCodec
					.builder(MaxStackSizeComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.intField(ComponentType.MAX_STACK_SIZE.getNamespacedKey(), MaxStackSizeComponent::getMaxStackSize, MaxStackSizeComponent::setMaxStackSize, 0)
					.build();
	
	int getMaxStackSize();
	
	void setMaxStackSize(int stackSize);
	
	@Override
	MaxStackSizeComponent clone();
	
	@Override
	default NBTCodec<? extends MaxStackSizeComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
