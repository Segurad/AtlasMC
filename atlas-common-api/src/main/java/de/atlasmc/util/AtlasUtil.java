package de.atlasmc.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public class AtlasUtil {
	
	private static final Function<?, ?> GET_SELF = v -> { return v; };
	private static final BiConsumer<?, ?> SET_VOID = (_, _) -> {};
	
	/**
	 * UUID with value 0
	 */
	public static final UUID ZERO_UUID = new UUID(0, 0);
	
	private AtlasUtil() {}
	
	public static <A, B> BiConsumer<A, B> getSetVoid() {
		@SuppressWarnings("unchecked")
		var function = (BiConsumer<A, B>) SET_VOID;
		return function;
	}
	
	public static <T> Function<T, T> getSelf() {
		@SuppressWarnings("unchecked")
		var function = (Function<T, T>) GET_SELF;
		return function;
	}
	
	@NotNull
	public static UUID uuidFromBytes(@NotNull byte[] bytes) {
		return uuidFromBytes(bytes, 0);
	}
	
	@NotNull
	public static String getShortUUID(@NotNull UUID uuid) {
		String raw = uuid.toString();
		int index = raw.indexOf('-');
		return raw.substring(0, index);
	}
	
	
	/**
	 * Converts the given bytes to a UUID. The given array must have at least 16 bytes available.
	 * @param bytes
	 * @param offset
	 * @return UUID
	 */
	@NotNull
	public static UUID uuidFromBytes(@NotNull byte[] bytes, int offset) {
		if (bytes.length - offset < 16)
			throw new IllegalArgumentException("Need at least 16 readable bytes: " + (bytes.length - offset));
		long most = bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		long least = bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		most = (most << 8) | bytes[offset++] & 0xFF;
		return new UUID(most, least);
	}
	
	@NotNull
	public static byte[] uuidToBytes(@NotNull UUID uuid) {
		return uuidToBytes(uuid, new byte[16], 0);
	}
	
	/**
	 * Converts the given UUID to bytes
	 * @param uuid
	 * @param buff
	 * @param offset
	 * @return
	 */
	@NotNull
	public static byte[] uuidToBytes(@NotNull UUID uuid, @NotNull byte[] buff, int offset) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		if (buff.length - offset < 16)
			throw new IllegalArgumentException("Can not write 16 bytes to buff: " + (buff.length - offset));
		long most = uuid.getMostSignificantBits();
		long least = uuid.getLeastSignificantBits();
		offset += 15;
		for (int i = 0; i < 8; i++) {
			buff[offset--] = (byte) (least & 0xFF);
			least >>>= 8;
		}
		for (int i = 0; i < 8; i++) {
			buff[offset--] = (byte) (most & 0xFF);
			most >>>= 8;
		}
		return buff;
	}
	
	/**
	 * Removes the element at the given index and fills the gap with the last element
	 * @param <T>
	 * @param list to remove from
	 * @param index to remove
	 * @return the removed element
	 */
	@Nullable
	public static <T> T fastRemove(@NotNull List<T> list, int index) {
		int last = list.size() - 1;
		T removed = list.set(index, list.get(last));
		list.set(last, null);
		return removed;
	}
	
	/**
	 * Removes the given element and fills the gap with the last element
	 * @param <T>
	 * @param list to remove from
	 * @param element to remove
	 * @return true if removed
	 */
	public static <T> boolean fastRemove(@NotNull List<T> list, @NotNull Object element) {
		if (element == null)
			throw new IllegalArgumentException("Element can not be null!");
		int index = list.indexOf(element);
		if (index == -1)
			return false;
		int last = list.size() - 1;
		T removed = list.set(index, list.get(last));
		list.set(last, null);
		return removed != null;
	}
	
	public static <T> T getSingleton(Class<T> clazz) {
		Method m;
		try {
			m = clazz.getDeclaredMethod("getInstance");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException("Error while fetching getInstance method!", e);
		}
		try {
			@SuppressWarnings("unchecked")
			T instance = (T) m.invoke(null);
			if (instance == null)
				throw new IllegalStateException("Singleton was null for type: " + clazz);
			return instance;
		} catch (ClassCastException | IllegalAccessException | InvocationTargetException e) {
			throw new IllegalArgumentException("Error while fetching instance!", e);
		}
	}

}
