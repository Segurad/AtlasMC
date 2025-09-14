package de.atlasmc.util.configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

/**
 * Section of a {@link Configuration} e.g. sub map or list
 */
public interface ConfigurationSection {

	default boolean contains(@NotNull String path) {
		return get(path, null) != null;
	}
	
	@Nullable
	default Object get(@NotNull String path) {
		return get(path, null);
	}
	
	@Nullable
	Object get(@NotNull String path, @Nullable Object def);
	
	@Nullable
	default ConfigurationSection getConfigurationSection(@NotNull String path) {
		Object value = get(path);
		if (value instanceof ConfigurationSection)
			return (ConfigurationSection) value;
		return null;
	}
	
	@Nullable
	default String getString(@NotNull String path) {
		return getString(path, null);
	}
	
	@Nullable
	default String getString(@NotNull String path, @Nullable String def) {
		Object val = get(path, def);
		return val == null ? def : val.toString();
	}
	
	default int getInt(@NotNull String path) {
		return getInt(path, 0);
	}
	
	default int getInt(@NotNull String path, int def) {
		Object val = get(path, null);
		return NumberConversion.toInt(val, def);
	}
	
	default long getLong(@NotNull String path) {
		return getLong(path, 0);
	}
	
	default long getLong(@NotNull String path, long def) {
		Object val = get(path, null);
		return NumberConversion.toLong(val, def);
	}

	default double getDouble(@NotNull String path) {
		return getDouble(path, 0.0);
	}

	default double getDouble(@NotNull String path, double def) {
		Object val = get(path, null);
		return NumberConversion.toDouble(val, def);
	}

	default boolean getBoolean(@NotNull String path) {
		return getBoolean(path, false);
	}

	default boolean getBoolean(@NotNull String path, boolean def) {
		Object val = get(path, def);
		return val instanceof Boolean ? (Boolean) val : def;
	}

	default float getFloat(@NotNull String path) {
		return getFloat(path, 0.0f);
	}

	default float getFloat(@NotNull String path, float def) {
		Object val = get(path, null);
		return NumberConversion.toFloat(val, def);
	}

	@Nullable
	default List<String> getStringList(@NotNull String path) {
		return getStringList(path, null);
	}

	@Nullable
	default List<String> getStringList(@NotNull String path, @Nullable List<String> def) {
		return getListOfType(path, String.class, def);
	}
	
	@Nullable
	default List<ConfigurationSection> getConfigurationList(@NotNull String path) {
		return getConfigurationList(path, null);
	}
	
	@Nullable
	default List<ConfigurationSection> getConfigurationList(@NotNull String path, @Nullable List<ConfigurationSection> def) {
		return getListOfType(path, ConfigurationSection.class, def);
	}

	@Nullable
	default List<?> getList(@NotNull String path) {
		return getList(path, null);
	}

	@Nullable
	default List<?> getList(@NotNull String path, @Nullable List<?> def) {
		Object val = get(path, null);
		return (val instanceof List) ? (List<?>) val : def;
	}

	@Nullable
	default <T> List<T> getListOfType(@NotNull String path, @NotNull Class<T> clazz) {
		return getListOfType(path, clazz, null);
	}

	@Nullable
	default <T> List<T> getListOfType(@NotNull String path, @NotNull Class<T> clazz, @Nullable List<T> def) {
		List<?> raw = getList(path, def);
		if (raw == null)
			return def;
		for (Object o : raw)
			if (!clazz.isInstance(o))
				return def;
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) raw;
		return list;
	}
	
	@NotNull
	ConfigurationSection createSection(@NotNull String path);
	
	@NotNull
	ListConfigurationSection createListSection(@NotNull String path);
	
	@NotNull
	Configuration getRoot();
	
	@Nullable
	ConfigurationSection getParent();
	
	/**
	 * Sets the value at the given path and returns the previous value.
	 * If the value is null and no {@link ConfigurationSection} is present at the paths end these sections will not be created and so the value will not be set.
	 * @param path
	 * @param value
	 * @return old value or null
	 */
	@Nullable
	Object set(@NotNull String path, @Nullable Object value);
	
	/**
	 * Adds the contents of the section to this configuration
	 * @param config
	 */
	default void addConfiguration(@NotNull ConfigurationSection config) {
		copySection(this, config);
	}
	
	/**
	 * Removes the last path element and returns the values currently set
	 * @param path
	 * @return value or null
	 */
	@Nullable
	Object remove(String path);
	
	@NotNull
	Map<String, Object> asMap();
	
	@NotNull
	Set<String> getKeys();
	
	/**
	 * Returns the number of elements in this section
	 * @return size
	 */
	int size();
	
	/**
	 * Removes all elements from this section
	 */
	void clear();
	
	/**
	 * Copies the contents of one section to another
	 * @param dest
	 * @param src
	 */
	public static void copySection(@NotNull ConfigurationSection dest, ConfigurationSection src) {
		if (src == null)
			return;
		src.asMap().forEach((key, value) -> {
			if (value instanceof ConfigurationSection child) {
				ConfigurationSection newSection = dest.createSection(key);
				copySection(newSection, child);
			} else {
				dest.set(key, value);
			}
		});
	}

}
