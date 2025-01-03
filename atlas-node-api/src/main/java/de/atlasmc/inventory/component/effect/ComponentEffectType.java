package de.atlasmc.inventory.component.effect;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public enum ComponentEffectType implements Namespaced, EnumName, EnumValueCache, EnumID {
	
	APPLY_EFFECTS,
	REMOVE_EFFECTS,
	CLEAR_ALL_EFFECTS,
	TELEPORT_RANDOMLY,
	PLAY_SOUND;

	private static List<ComponentEffectType> VALUES;
	
	private final NamespacedKey key;
	
	private ComponentEffectType() {
		String key = "minecraft:" + name().toLowerCase();
		this.key = NamespacedKey.literal(key.intern());
	}
	
	@Override
	public String getName() {
		return key.getKey();
	}
	
	@Override
	public int getID() {
		return ordinal();
	}
	
	public static ComponentEffectType getByID(int id) {
		return getValues().get(id);
	}
	
	/**
	 * Returns the value represented by the name or null if no matching value has been found
	 * @param name the name of the value
	 * @return value or null
	 */
	public static ComponentEffectType getByName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		List<ComponentEffectType> values = getValues();
		final int size = values.size();
		for (int i = 0; i < size; i++) {
			ComponentEffectType value = values.get(i);
			if (value.key.getKey().equals(name)) 
				return value;
		}
		return null;
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<ComponentEffectType> getValues() {
		if (VALUES == null)
			VALUES = List.of(values());
		return VALUES;
	}
	
	/**
	 * Releases the system resources used from the values cache
	 */
	public static void freeValues() {
		VALUES = null;
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}

}
