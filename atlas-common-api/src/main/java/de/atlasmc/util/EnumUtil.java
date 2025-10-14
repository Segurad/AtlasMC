package de.atlasmc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.IDHolder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class EnumUtil {
	
	private static final ConcurrentHashMap<Class<?>, EnumData<?>> DATA = new ConcurrentHashMap<>();
	
	public static <T extends Enum<T>> boolean hasData(Class<T> clazz) {
		return DATA.containsKey(clazz);
	}
	
	public static <T extends Enum<T>> EnumData<T> getData(Class<T> clazz) {
		@SuppressWarnings("unchecked")
		EnumData<T> data = (EnumData<T>) DATA.get(clazz);
		if (data == null) {
			DATA.put(clazz, data = new EnumData<>(clazz));
		}
		return data;
	}
	
	public static <T extends Enum<T>> List<T> getValues(Class<T> clazz) {
		return getData(clazz).getValues();
	}
	
	public static <T extends Enum<T> & IDHolder> T getByID(Class<T> clazz, int id) {
		return getData(clazz).getByID(id);
	}
	
	public static <T extends Enum<T> & EnumName> T getByName(Class<T> clazz, String name) {
		return getData(clazz).byName.get(name);
	}
	
	private static <T extends Enum<T>> boolean hasMatchingIDs(T[] values) {
		for (T value : values) {
			if (((IDHolder) value).getID() != value.ordinal())
				return false;
		}
		return true;
	}
	
	public static final class EnumData<T extends Enum<T>> {
		
		final List<T> values;
		final Map<String, T> byName;
		final Int2ObjectMap<T> byID;
		
		public EnumData(Class<T> clazz) {
			T[] values = clazz.getEnumConstants();
			this.values = List.of(values);
			if (IDHolder.class.isAssignableFrom(clazz)) {
				if (hasMatchingIDs(values)) {
					byID = null;
				} else {
					Int2ObjectOpenHashMap<T> entries = new Int2ObjectOpenHashMap<T>(values.length);
					for (T value : values) {
						int id = ((IDHolder) value).getID();
						if (entries.containsKey(id))
							continue;
						entries.put(id, value);
					}
					@SuppressWarnings("unchecked")
					Int2ObjectMap.Entry<T>[] entryValues = new Int2ObjectMap.Entry[entries.size()];
					byID = Int2ObjectMap.ofEntries(entries.int2ObjectEntrySet().toArray(entryValues));
				}
			} else {
				byID = null;
			}
			if (EnumName.class.isAssignableFrom(clazz)) {
				Map<String, T> entries = new HashMap<String, T>();
				for (T value : values) {
					String name = ((EnumName) value).getName();
					if (entries.containsKey(name))
						continue;
					entries.put(name, value);
				}
				byName = Map.copyOf(entries);
			} else {
				byName = Map.of();
			}
		}
		
		public final T getByID(int id) {
			return byID == null ? values.get(id) : byID.get(id);
		}
		
		public final List<T> getValues() {
			return values;
		}
		
		public final T getByName(String name) {
			return byName.get(name);
		}
		
	}

}
