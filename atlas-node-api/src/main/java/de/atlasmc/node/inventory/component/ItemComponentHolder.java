package de.atlasmc.node.inventory.component;

import java.util.Map;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ItemComponentHolder {
	
	public static final NBTCodec<ItemComponentHolder>
	NBT_HANDLER = NBTCodec
					.builder(ItemComponentHolder.class)
					.compoundMapNamespacedType2Type("components", ItemComponentHolder::hasComponents, ItemComponentHolder::getComponents, ItemComponent.NBT_HANDLER, ItemComponent::getType)
					.build();
	
	@NotNull
	Map<ComponentType, ItemComponent> getComponents();

	/**
	 * Returns whether or not this ItemStack has components
	 * @return true if components present
	 */
	boolean hasComponents();
	
	/**
	 * Returns whether or not a component with the given type is present
	 * @param type to check
	 * @return true if present
	 */
	default boolean hasComponent(@NotNull ComponentType type) {
		if (!hasComponents())
			return false;
		return getComponents().containsKey(type);
	}
	
	@NotNull
	default <T extends ItemComponent> T getComponent(@NotNull ComponentType type) {
		if (type == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (hasComponents()) {
			@SuppressWarnings("unchecked")
			T component = (T) getComponents().get(type);
			if (component != null)
				return component;
		}
		@SuppressWarnings("unchecked")
		T component = (T) type.createItemComponent();
		getComponents().put(type, component);
		return component;
	}
	
	/**
	 * Sets a new ItemComponent and returns the previous value
	 * @param component to set
	 * @return component or null
	 */
	@Nullable
	default ItemComponent setComponent(@NotNull ItemComponent component) {
		if (component == null)
			throw new IllegalArgumentException("Component can not be null!");
		return getComponents().put(component.getType(), component);
	}
	
	/**
	 * Removes the component with the given type
	 * @param type to remove
	 * @return component or null
	 */
	@Nullable
	default ItemComponent removeComponent(@NotNull ComponentType type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		if (hasComponents())
			return null;
		return getComponents().remove(type);
	}
	
}
