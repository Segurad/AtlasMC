package de.atlasmc.util.nbt.io;

import java.io.Closeable;
import java.io.IOException;
import java.util.UUID;
import java.util.function.LongSupplier;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.NBT;

/**
 * Interface for writing data as NBT.
 */
public interface NBTWriter extends Closeable {
	
	void writeEndTag() throws IOException;
	
	void writeByteTag(CharSequence name, int value) throws IOException;
	
	default void writeByteTag(CharSequence name, boolean value) throws IOException {
		writeByteTag(name, value ? 1 : 0);
	}
	
	void writeShortTag(CharSequence name, int value) throws IOException;
	
	void writeIntTag(CharSequence name, int value) throws IOException;
	
	void writeLongTag(CharSequence name, long value) throws IOException;
	
	void writeFloatTag(CharSequence name, float value) throws IOException;
	
	void writeDoubleTag(CharSequence name, double value) throws IOException;
	
	default void writeByteArrayTag(CharSequence name, byte[] data) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		writeByteArrayTag(name, data, 0, data.length);
	}
	
	void writeByteArrayTag(CharSequence name, byte[] data, int offset, int length) throws IOException;
	
	void writeStringTag(CharSequence name, String value) throws IOException;
	
	default void writeNamespacedKey(CharSequence name, NamespacedKey key) throws IOException {
		writeStringTag(name, key.toString());
	}
	
	void writeListTag(CharSequence name, TagType payloadType, int payloadsize) throws IOException;
	
	default void writeCompoundTag() throws IOException {
		writeCompoundTag(null);
	}
	
	void writeCompoundTag(CharSequence name) throws IOException;
	
	default void writeEmptyCompound(CharSequence name) throws IOException {
		writeCompoundTag(name);
		writeEndTag();
	}
	
	default void writeIntArrayTag(CharSequence name, int[] data) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		writeIntArrayTag(name, data, 0, data.length);
	}
	
	void writeIntArrayTag(CharSequence name, int[] data, int offset, int length) throws IOException;
	
	/**
	 * Writes a UUID as {@link TagType#INT_ARRAY}
	 * @param name
	 * @param uuid
	 * @throws IOException
	 */
	void writeUUID(CharSequence name, UUID uuid) throws IOException;
	
	default void writeLongArrayTag(CharSequence name, long[] data) throws IOException {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		writeLongArrayTag(name, data, 0, data.length);
	}
	
	void writeLongArrayTag(CharSequence name, long[] data, int offset, int length) throws IOException;
	
	void writeLongArrayTag(CharSequence name, int length, LongSupplier supplier) throws IOException;
	
	void writeNBT(NBT nbt) throws IOException;
	
	void writeNBT(CharSequence name, NBT nbt) throws IOException;
	
}
