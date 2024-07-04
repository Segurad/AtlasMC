package de.atlasmc.util;

import java.util.UUID;

public class AtlasUtil {
	
	public static final UUID ZERO_UUID = new UUID(0, 0);
	
	private AtlasUtil() {}
	
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

}
