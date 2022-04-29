package de.atlasmc.util.nbt.io;

import java.io.Closeable;
import java.io.IOException;
import java.util.UUID;
import java.util.function.LongSupplier;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.NBT;

public interface NBTWriter extends Closeable {
	
	public void writeEndTag() throws IOException;
	
	public void writeByteTag(String name, int value) throws IOException;
	
	public default void writeByteTag(String name, boolean value) throws IOException {
		writeByteTag(name, value ? 1 : 0);
	}
	
	public void writeShortTag(String name, int value) throws IOException;
	
	public void writeIntTag(String name, int value) throws IOException;
	
	public void writeLongTag(String name, long value) throws IOException;
	
	public void writeFloatTag(String name, float value) throws IOException;
	
	public void writeDoubleTag(String name, double value) throws IOException;
	
	public default void writeByteArrayTag(String name, byte[] data) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		writeByteArrayTag(name, data, 0, data.length);
	}
	
	public void writeByteArrayTag(String name, byte[] data, int offset, int length) throws IOException;
	
	public void writeStringTag(String name, String value) throws IOException;
	
	public void writeListTag(String name, TagType payloadType, int payloadsize) throws IOException;
	
	public default void writeCompoundTag() throws IOException {
		writeCompoundTag(null);
	}
	
	public void writeCompoundTag(String name) throws IOException;
	
	public default void writeEmptyCompound(String name) throws IOException {
		writeCompoundTag(name);
		writeEndTag();
	}
	
	public default void writeIntArrayTag(String name, int[] data) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		writeIntArrayTag(name, data, 0, data.length);
	}
	
	public void writeIntArrayTag(String name, int[] data, int offset, int length) throws IOException;
	
	/**
	 * Writes a UUID as IntArrayTag
	 * @param name
	 * @param uuid
	 * @throws IOException
	 */
	public void writeUUID(String name, UUID uuid) throws IOException;
	
	public default void writeLongArrayTag(String name, long[] data) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		writeLongArrayTag(name, data, 0, data.length);
	}
	
	public void writeLongArrayTag(String name, long[] data, int offset, int length) throws IOException;
	
	public void writeLongArrayTag(String name, int length, LongSupplier supplier) throws IOException;
	
	public void writeNBT(NBT nbt) throws IOException;
	
}
