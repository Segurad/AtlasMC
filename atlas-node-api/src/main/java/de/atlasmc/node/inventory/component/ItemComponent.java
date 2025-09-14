package de.atlasmc.node.inventory.component;

import de.atlasmc.io.IOReadable;
import de.atlasmc.io.IOWriteable;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ItemComponent extends NBTSerializable, Cloneable, IOReadable, IOWriteable {
	
	public static final NBTSerializationHandler<ItemComponent> 
	NBT_HANDLER = NBTSerializationHandler
					.builder(ItemComponent.class)
					.fieldKeyConstructor(ComponentType.REGISTRY_KEY, ComponentType::createItemComponent)
					.build();
	
	/**
	 * Returns the type of this component
	 * @return type
	 */
	@NotNull
	ComponentType getType();
	
	ItemComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
