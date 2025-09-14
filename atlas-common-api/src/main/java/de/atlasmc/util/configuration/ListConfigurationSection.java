package de.atlasmc.util.configuration;

import java.util.List;

import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface ListConfigurationSection extends ConfigurationSection, List<Object> {
	
	<T> List<T> asList();
	
	<T> List<T> asListOfType(Class<T> clazz);
	
	@Nullable
	default Object get(int index) {
		return get(index, null);
	}
	
	
	@Nullable
	Object get(int index, Object def);
	
	@Nullable
	default ConfigurationSection getConfigurationSection(int index) {
		Object value = get(index);
		if (value instanceof ConfigurationSection)
			return (ConfigurationSection) value;
		return null;
	}
	
	@NotNull
	ConfigurationSection addSection();
	
	@NotNull
	ListConfigurationSection addListSection();
	
	@Nullable
	default String getString(int index) {
		return getString(index, null);
	}
	
	@Nullable
	default String getString(int index, @Nullable String def) {
		Object val = get(index, def);
		return val == null ? def : val.toString();
	}
	
	default int getInt(int index) {
		return getInt(index, 0);
	}
	
	default int getInt(int index, int def) {
		Object val = get(index, null);
		return NumberConversion.toInt(val, def);
	}
	
	default long getLong(int index) {
		return getLong(index, 0);
	}
	
	default long getLong(int index, long def) {
		Object val = get(index, null);
		return NumberConversion.toLong(val, def);
	}

	default double getDouble(int index) {
		return getDouble(index, 0.0);
	}

	default double getDouble(int index, double def) {
		Object val = get(index, null);
		return NumberConversion.toDouble(val, def);
	}

	default boolean getBoolean(int index) {
		return getBoolean(index, false);
	}

	default boolean getBoolean(int index, boolean def) {
		Object val = get(index, def);
		return val instanceof Boolean ? (Boolean) val : def;
	}

	default float getFloat(int index) {
		return getFloat(index, 0.0f);
	}

	default float getFloat(int index, float def) {
		Object val = get(index, null);
		return NumberConversion.toFloat(val, def);
	}

	@Nullable
	default List<String> getStringList(int index) {
		return getStringList(index, null);
	}

	@Nullable
	default List<String> getStringList(int index, @Nullable List<String> def) {
		return getListOfType(index, String.class, def);
	}
	
	@Nullable
	default List<ConfigurationSection> getConfigurationList(int index) {
		return getConfigurationList(index, null);
	}
	
	@Nullable
	default List<ConfigurationSection> getConfigurationList(int index, @Nullable List<ConfigurationSection> def) {
		return getListOfType(index, ConfigurationSection.class, def);
	}

	@Nullable
	default List<?> getList(int index) {
		return getList(index, null);
	}

	@Nullable
	default List<?> getList(int index, @Nullable List<?> def) {
		Object val = get(index, null);
		return (val instanceof List) ? (List<?>) val : def;
	}

	@Nullable
	default <T> List<T> getListOfType(int index, @NotNull Class<T> clazz) {
		return getListOfType(index, clazz, null);
	}

	@Nullable
	default <T> List<T> getListOfType(int index, @NotNull Class<T> clazz, @Nullable List<T> def) {
		List<?> raw = getList(index, def);
		if (raw == null)
			return def;
		for (Object o : raw)
			if (!clazz.isInstance(o))
				return def;
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) raw;
		return list;
	}

}
