package de.atlasmc.util.nbt.io;

import java.io.Closeable;
import java.io.IOException;
import java.util.UUID;
import java.util.function.LongSupplier;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.NBT;

public interface NBTWriter extends Closeable {
	
	public void writeEndTag() throws IOException;
	
	public void writeByteTag(CharSequence name, int value) throws IOException;
	
	public default void writeByteTag(CharSequence name, boolean value) throws IOException {
		writeByteTag(name, value ? 1 : 0);
	}
	
	public void writeShortTag(CharSequence name, int value) throws IOException;
	
	public void writeIntTag(CharSequence name, int value) throws IOException;
	
	public void writeLongTag(CharSequence name, long value) throws IOException;
	
	public void writeFloatTag(CharSequence name, float value) throws IOException;
	
	public void writeDoubleTag(CharSequence name, double value) throws IOException;
	
	public default void writeByteArrayTag(String name, byte[] data) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		writeByteArrayTag(name, data, 0, data.length);
	}
	
	public void writeByteArrayTag(CharSequence name, byte[] data, int offset, int length) throws IOException;
	
	public void writeStringTag(CharSequence name, String value) throws IOException;
	
	public void writeListTag(CharSequence name, TagType payloadType, int payloadsize) throws IOException;
	
	public default void writeCompoundTag() throws IOException {
		writeCompoundTag(null);
	}
	
	public void writeCompoundTag(CharSequence name) throws IOException;
	
	public default void writeEmptyCompound(CharSequence name) throws IOException {
		writeCompoundTag(name);
		writeEndTag();
	}
	
	public default void writeIntArrayTag(CharSequence name, int[] data) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		writeIntArrayTag(name, data, 0, data.length);
	}
	
	public void writeIntArrayTag(CharSequence name, int[] data, int offset, int length) throws IOException;
	
	/**
	 * Writes a UUID as IntArrayTag
	 * @param name
	 * @param uuid
	 * @throws IOException
	 */
	public void writeUUID(CharSequence name, UUID uuid) throws IOException;
	
	public default void writeLongArrayTag(CharSequence name, long[] data) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		writeLongArrayTag(name, data, 0, data.length);
	}
	
	public void writeLongArrayTag(CharSequence name, long[] data, int offset, int length) throws IOException;
	
	public void writeLongArrayTag(CharSequence name, int length, LongSupplier supplier) throws IOException;
	
	public void writeNBT(NBT nbt) throws IOException;
	
}
