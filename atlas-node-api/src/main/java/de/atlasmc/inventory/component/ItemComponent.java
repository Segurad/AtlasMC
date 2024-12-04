package de.atlasmc.inventory.component;

import java.lang.reflect.Field;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.nbt.NBTHolder;

public interface ItemComponent extends NBTHolder, Namespaced, Cloneable {
	
	/**
	 * Returns the value of the static COMPONENT_KEY field of the given class or null if not present
	 * @param clazz
	 * @return key or null
	 */
	public static NamespacedKey getComponentKey(Class<? extends ItemComponent> clazz) {
		NamespacedKey key = null;
		try {
			Field f = clazz.getDeclaredField("COMPONENT_KEY");
			f.setAccessible(true);
			key = (NamespacedKey) f.get(null);
		} catch (NoSuchFieldException e) {
			// nothing
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalStateException("Error while accessing COMPONENT_KEY field of class: " + clazz.getName(), e);
		}
		return key;
	}
	
	ItemComponent clone();
	
	boolean isServerOnly();

}
