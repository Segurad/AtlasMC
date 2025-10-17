package de.atlasmc.node.inventory.component;

import de.atlasmc.io.IOReadable;
import de.atlasmc.io.IOWriteable;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ItemComponent extends NBTSerializable, Cloneable, IOReadable, IOWriteable {
	
	public static final NBTCodec<ItemComponent> 
	NBT_HANDLER = NBTCodec
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
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
