package de.atlasmc.component;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface ComponentHolder<T extends Component> {

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
	boolean hasComponent(@NotNull ComponentType type);
	
	/**
	 * Returns the component with the given type.
	 * If no component is set with the type return null.
	 * @param <R>
	 * @param type
	 * @return component or null
	 */
	@Nullable
	<R extends T> R getComponent(@NotNull ComponentType type);
	
	/**
	 * Returns the component of the given type.
	 * If no component is set with the type a new one will be created
	 * @param <R>
	 * @param type
	 * @return
	 */
	@NotNull
	<R extends T> R getOrCreateComponent(@NotNull ComponentType type);
	
	/**
	 * Sets a new {@link Component} and returns the previous value
	 * @param component to set
	 * @return component or null
	 */
	@Nullable
	T setComponent(@NotNull T component);
	
	/**
	 * Removes the component with the given type
	 * @param type to remove
	 * @return component or null
	 */
	@Nullable
	T removeComponent(@NotNull ComponentType type);
	
	/**
	 * Removes the component
	 * @param type to remove
	 * @return true removed
	 */
	@Nullable
	boolean removeComponent(@NotNull Component component);

}
