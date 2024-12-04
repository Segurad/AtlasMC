package de.atlasmc.util;

import java.util.List;
import java.util.UUID;

public class AtlasUtil {
	
	/**
	 * UUID with value 0
	 */
	public static final UUID ZERO_UUID = new UUID(0, 0);
	
	private AtlasUtil() {}
	
	public static UUID uuidFromBytes(byte[] bytes) {
		return uuidFromBytes(bytes, 0);
	}
	
	/**
	 * Converts the given bytes to a UUID. The given array must have at least 16 bytes available.
	 * @param bytes
	 * @param offset
	 * @return UUID
	 */
	public static UUID uuidFromBytes(byte[] bytes, int offset) {
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
	
	public static byte[] uuidToBytes(UUID uuid) {
		return uuidToBytes(uuid, new byte[16], 0);
	}
	
	/**
	 * Converts the given UUID to bytes
	 * @param uuid
	 * @param buff
	 * @param offset
	 * @return
	 */
	public static byte[] uuidToBytes(UUID uuid, byte[] buff, int offset) {
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
	public static <T> T fastRemove(List<T> list, int index) {
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
	public static <T> boolean fastRemove(List<T> list, Object element) {
		int index = list.indexOf(element);
		if (index == -1)
			return false;
		int last = list.size() - 1;
		T removed = list.set(index, list.get(last));
		list.set(last, null);
		return removed != null;
	}

}
