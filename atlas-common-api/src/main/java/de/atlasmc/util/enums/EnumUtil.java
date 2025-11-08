package de.atlasmc.util.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@ThreadSafe
public class EnumUtil {
	
	private static final ConcurrentHashMap<Class<?>, EnumData<?>> DATA = new ConcurrentHashMap<>();
	/**
	 * Lock used to initialize new EnumData
	 */
	private static final Object LOCK = new Object();
	
	/**
	 * Returns whether or not a EnumData representation is present for the given class.
	 * @param <T>
	 * @param clazz
	 * @return true if present
	 */
	public static <T extends Enum<T>> boolean hasData(Class<T> clazz) {
		return DATA.containsKey(clazz);
	}
	
	/**
	 * Returns the EnumData representation for a the given class.
	 * This will initialize new {@link EnumData} in case it is not present.
	 * @param <T>
	 * @param clazz
	 * @return data
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Enum<?>> EnumData<T> getData(Class<T> clazz) {
		EnumData<T> data = (EnumData<T>) DATA.get(clazz);
		if (data != null)
			return data;
		synchronized (LOCK) {
			data = (EnumData<T>) DATA.get(clazz);
			if (data != null)
				return data;
			DATA.put(clazz, data = new EnumData<>(clazz));
			return data;
		}
	}
	
	/**
	 * Returns a immutable list containing all enum constants of the given class.
	 * @param <T>
	 * @param clazz
	 * @return values
	 */
	public static <T extends Enum<?>> List<T> getValues(Class<T> clazz) {
		return getData(clazz).getValues();
	}
	
	/**
	 * @see EnumData#getByID(int)
	 */
	public static <T extends Enum<?> & IDHolder> T getByID(Class<T> clazz, int id) {
		return getData(clazz).getByID(id);
	}
	
	/**
	 * @see EnumData#getByName(String)
	 */
	public static <T extends Enum<?> & EnumName> T getByName(Class<T> clazz, String name) {
		return getData(clazz).byName.get(name);
	}
	
	public static <T, E extends Enum<?> & IDHolder> NBTCodec<T> enumIntNBTCodec(Class<E> clazz) {
		return new EnumIntNBTCodec<>(clazz);
	}
	
	public static <T, E extends Enum<?> & IDHolder> NBTCodec<T> enumByteNBTCodec(Class<E> clazz) {
		return new EnumByteNBTCodec<>(clazz);
	}
	
	public static <T, E extends Enum<?> & EnumName> NBTCodec<T> enumStringNBTCodec(Class<E> clazz) {
		return new EnumStringNBTCodec<>(clazz);
	}
	
	/**
	 * Type storing information about a enum.
	 * Is aware of {@link EnumName} and {@link IDHolder} for byName and byID mappings
	 * @param <T>
	 */
	public static final class EnumData<T extends Enum<?>> {
		
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
			final boolean isEnumName = EnumName.class.isAssignableFrom(clazz);
			final Map<String, T> entries = new HashMap<String, T>(values.length * 2);
			for (T value : values) {
				entries.put(value.name(), value);
				if (isEnumName) {
					String name = ((EnumName) value).getName();
					entries.putIfAbsent(name, value);
				}
			}
			byName = Map.copyOf(entries);
		}
		
		/**
		 * Returns the enum represented by a id.
		 * By default this id is equal to {@link Enum#ordinal()}
		 * In case the enum implements {@link IDHolder} the id returned by {@link IDHolder#getID()} will be used for the mapping.
		 * @param id
		 * @return enum or null
		 */
		@Nullable
		public final T getByID(int id) {
			return byID == null ? id > values.size() ? null : values.get(id) : byID.get(id);
		}
		
		/**
		 * Returns a immutable list containing all enum constants
		 * @return values
		 */
		@NotNull
		public final List<T> getValues() {
			return values;
		}
		
		/**
		 * Returns the enum represented by the given string.
		 * This may be the {@link Enum#name()}. 
		 * In case the enum implements {@link EnumName} the enum with matching {@link EnumName#getName()} will be returned.
		 * If multiple with the implemented interface return the same name only the first enum will be retrievable.
		 * @param name
		 * @return enum or null
		 */
		@Nullable
		public final T getByName(String name) {
			return byName.get(name);
		}
		
		/**
		 * Returns a immutable map that maps name to enum.
		 * If the enum implements {@link EnumName} it maps {@link EnumName#getName()} to the value only the first enum with a name will be mapped.
		 * Every enum of the type is mapped by {@link Enum#name()} so it is possible to retrieve values by the enum and its given name.
		 * @return byName map
		 */
		@NotNull
		public final Map<String, T> getByName() {
			return byName;
		}
		
		/**
		 * Checks whether or not all {@link IDHolder#getID()} match {@link Enum#ordinal()}.
		 * In case of matching ids we don't need to initialize {@link #byID}
		 * @param <T>
		 * @param values
		 * @return true if matching
		 */
		private static <T extends Enum<?>> boolean hasMatchingIDs(T[] values) {
			for (T value : values) {
				if (((IDHolder) value).getID() != value.ordinal())
					return false;
			}
			return true;
		}
		
	}

}
