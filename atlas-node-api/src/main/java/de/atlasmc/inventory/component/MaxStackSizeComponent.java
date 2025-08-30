package de.atlasmc.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface MaxStackSizeComponent extends ItemComponent {

	public static final NBTSerializationHandler<MaxStackSizeComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(MaxStackSizeComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.intField(ComponentType.MAX_STACK_SIZE.getNamespacedKey(), MaxStackSizeComponent::getMaxStackSize, MaxStackSizeComponent::setMaxStackSize, 0)
					.build();
	
	int getMaxStackSize();
	
	void setMaxStackSize(int stackSize);
	
	MaxStackSizeComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends MaxStackSizeComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
