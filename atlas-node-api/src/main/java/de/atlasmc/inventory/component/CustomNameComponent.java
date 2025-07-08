package de.atlasmc.inventory.component;

import de.atlasmc.Nameable;
import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface CustomNameComponent extends ItemComponent, Nameable {
	
	public static NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:custom_name");
	
	public static final NBTSerializationHandler<CustomNameComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(CustomNameComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.include(Nameable.NBT_HANDLER)
					.build();
	
	CustomNameComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends CustomNameComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
