package de.atlasmc.inventory.component;

import java.util.Map;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ItemComponentHolder {
	
	public static final NBTSerializationHandler<ItemComponentHolder>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ItemComponentHolder.class)
					.compoundMapNamespacedType("components", ItemComponentHolder::hasComponents, ItemComponentHolder::getComponents, ItemComponent.NBT_HANDLER)
					.build();
	
	Map<NamespacedKey, ItemComponent> getComponents();

	/**
	 * Returns whether or not this ItemStack has components
	 * @return true if components present
	 */
	boolean hasComponents();
	
	/**
	 * Returns whether or not a component with the given key is present
	 * @param key to check
	 * @return true if present
	 */
	default boolean hasComponent(NamespacedKey key) {
		if (!hasComponents())
			return false;
		return getComponents().containsKey(key);
	}
	
	default <T extends ItemComponent> T getComponent(NamespacedKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (hasComponents()) {
			@SuppressWarnings("unchecked")
			T component = (T) getComponents().get(key);
			if (component != null)
				return component;
		}
		Registry<ItemComponentFactory> registry = Registries.getRegistry(ItemComponentFactory.class);
		ItemComponentFactory factory = registry.get(key);
		if (factory == null)
			return null;
		@SuppressWarnings("unchecked")
		T component = (T) factory.createComponent();
		getComponents().put(key, component);
		return component;
	}
	
	default <T extends ItemComponent> T getComponent(Class<T> clazz) {
		return getComponent(ItemComponent.getComponentKey(clazz));
	}
	
	/**
	 * Sets a new ItemComponent and returns the previous value
	 * @param component to set
	 * @return component or null
	 */
	default ItemComponent setComponent(ItemComponent component) {
		if (component == null)
			throw new IllegalArgumentException("Component can not be null!");
		return getComponents().put(component.getNamespacedKey(), component);
	}
	
	/**
	 * Removes the component with the given key
	 * @param key to remove
	 * @return component or null
	 */
	default ItemComponent removeComponent(NamespacedKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (hasComponents())
			return null;
		return getComponents().remove(key);
	}
	
}
