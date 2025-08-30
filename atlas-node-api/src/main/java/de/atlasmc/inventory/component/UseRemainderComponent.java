package de.atlasmc.inventory.component;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface UseRemainderComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<UseRemainderComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(UseRemainderComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeCompoundField(ComponentType.USE_REMAINDER.getNamespacedKey(), UseRemainderComponent::getItem, UseRemainderComponent::setItem, ItemStack.NBT_HANDLER)
					.build();
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	UseRemainderComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends UseRemainderComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
