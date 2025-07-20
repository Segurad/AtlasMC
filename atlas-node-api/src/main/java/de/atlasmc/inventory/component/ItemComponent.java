package de.atlasmc.inventory.component;

import java.lang.reflect.Field;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.io.IOReadable;
import de.atlasmc.io.IOWriteable;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ItemComponent extends NBTSerializable, Namespaced, Cloneable, IOReadable, IOWriteable {
	
	public static final NBTSerializationHandler<ItemComponent> 
	NBT_HANDLER = NBTSerializationHandler
					.builder(ItemComponent.class)
					.fieldKeyConstructor(Registries.getRegistry(ItemComponentFactory.class), ItemComponentFactory::createComponent)
					.build();
	
	/**
	 * Returns the value of the static COMPONENT_KEY field of the given class
	 * @param clazz
	 * @return key
	 */
	@NotNull
	public static NamespacedKey getComponentKey(Class<? extends ItemComponent> clazz) {
		Field field;
		try {
			field = clazz.getDeclaredField("COMPONENT_KEY");
		} catch (NoSuchFieldException | SecurityException e) {
			throw new IllegalArgumentException("Failed to fetch field COMPONENT_KEY from class: " + clazz.getName());
		}
		field.setAccessible(true);
		NamespacedKey key;
		try {
			key = (NamespacedKey) field.get(null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalArgumentException("Failed to fetch value from COMPONENT_KEY field form clazz: " + clazz.getName());
		}
		return key;
	}
	
	/**
	 * Returns the type of this component may be null for custom components
	 * @return type or null
	 */
	@Nullable
	ComponentType getType();
	
	ItemComponent clone();

	boolean isServerOnly();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
