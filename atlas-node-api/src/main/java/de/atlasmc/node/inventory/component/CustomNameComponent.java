package de.atlasmc.node.inventory.component;

import de.atlasmc.node.Nameable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface CustomNameComponent extends ItemComponent, Nameable {
	
	public static final NBTSerializationHandler<CustomNameComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(CustomNameComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.chat(ComponentType.CUSTOM_NAME.getNamespacedKey(), CustomNameComponent::getCustomName, CustomNameComponent::setCustomName)
					.build();
	
	CustomNameComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends CustomNameComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
