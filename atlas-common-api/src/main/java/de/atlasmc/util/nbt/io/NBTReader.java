package de.atlasmc.util.nbt.io;

import java.io.Closeable;
import java.io.IOException;
import java.util.UUID;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.ListTag;
import de.atlasmc.util.nbt.tag.NBT;

/**
 * Reader for NBT Data<br>
 * <h2>Usage Examples</h2><br>
 * Read {@link TagType#LIST}
 * <pre>
 * reader.readNextEntry(); // skip to first element of list
 * while (reader.getRestPayload() > 0) {
 * 	// read element
 * }
 * reader.readNextEntry(); // skip to next element after list
 * </pre>
 * Read {@link TagType#COMPOUND}
 * <pre>
 * reader.readNextEntry(); // skip to first element of compound
 * while (reader.getType() != TagType.TAG_END) {
 * 	switch (reader.getFieldName()) {
 * 	case MY_ELEMENT_1:
 * 		// read element
 * 	default:
 * 		reader.skipTag(); // or throw exception if you want
 * 	}
 * }
 * reader.readNextEntry(); // skip to next after compound over TAG_END
 * </pre>
 * Read {@link TagType#LIST} of {@link TagType#COMPOUND}
 * <pre>
 * reader.readNextEntry(); // skip to first element of list
 * while (reader.getRestPayload() > 0) {
 *  reader.readNextEntry(); // skip to first element of compound
 * 	int element_1 = 0;
 * 	while (reader.getType() != TagType.TAG_END) {
 * 		switch (reader.getFieldName()) {
 * 		case ELEMENT_1:
 * 			element_1 = reader.readIntTag(); // read element
 * 		default:
 * 			reader.skipTag(); // or throw exception
 * 		}
 * 	}
 * 	// assemble your object
 * 	reader.readNextEntry(); // skip to next compound over TAG_END
 * }
 * reader.readNextEntry(); // skip to next element after list
 * </pre>
 */
public interface NBTReader extends Closeable {
	
	/**
	 * Returns the Depth in the current NBT Structure.
	 * The depth will increase by 1 for each ListTag or CompoundTag entered with {@link #readNextEntry()}.
	 * For ListTag of CompoundTag this increase will be 2 because the first compound is automatically entered.
	 * @return
	 * @throws IOException 
	 */
	int getDepth() throws IOException;
	
	/**
	 * Returns the current fields name as {@link CharSequence} or null
	 * @return sequence or null
	 * @throws IOException 
	 */
	@Nullable
	CharSequence getFieldName() throws IOException;
	
	/**
	 * Returns the {@link TagType} of highest list contents or null
	 * @return tag type or null
	 * @throws IOException 
	 */
	@Nullable
	TagType getListType() throws IOException;
	
	/**
	 * Returns the number of elements remaining in the currently entered list {@link ListTag} or -1 if no current entered list.
	 * May be 1 for more than one element in some implementations.
	 * @return number of elements or -1
	 * @throws IOException 
	 */
	int getRestPayload() throws IOException;
	
	/**
	 * Returns the number of elements remaining in the ListTag or -1 if {@link #getType()} is not {@link TagType#LIST} 
	 * @return number of elements or -1
	 * @throws IOException 
	 */
	int getNextPayload() throws IOException;
	
	/**
	 * Returns the type of the current Tag
	 * @return tag type
	 * @throws IOException 
	 */
	@NotNull
	TagType getType() throws IOException;
	
	/**
	 * Returns whether or not the next read element is a entry of a list tag
	 * @return true if list
	 */
	boolean isList();
	
	/**
	 * Returns the number of remaining elements of the current array tag.
	 * or -1 if no array tag
	 * <h2> Array Tag Types may be</h2>
	 * <ul>
	 * <li> {@link TagType#BYTE_ARRAY}</li>
	 * <li> {@link TagType#INT_ARRAY}</li>
	 * <li> {@link TagType#LONG_ARRAY}</li>
	 * </ul>
	 * @return number of elements
	 */
	int getArrayTagPayload();
	
	void readByteArrayTag(IntConsumer dataConsumer) throws IOException;
	
	/**
	 * Reads a byte array tag to the given buffer.
	 * @param buf
	 * @return number of read bytes
	 * @throws IOException
	 */
	int readByteArrayTag(byte[] buf) throws IOException;
	
	byte[] readByteArrayTag() throws IOException;
	
	byte readByteTag() throws IOException;
	
	double readDoubleTag() throws IOException;
	
	float readFloatTag() throws IOException;
	
	void readIntArrayTag(IntConsumer dataConsumer) throws IOException;
	
	/**
	 * Reads a int array tag to the given buffer.
	 * @param buf
	 * @return number of read integer
	 * @throws IOException
	 */
	int readIntArrayTag(int[] buf) throws IOException;
	
	int[] readIntArrayTag() throws IOException;
	
	int readIntTag() throws IOException;
	
	void readLongArrayTag(LongConsumer dataConsumer) throws IOException;
	
	/**
	 * Reads a long array tag to the given buffer.
	 * @param buf
	 * @return number of read long
	 * @throws IOException
	 */
	int readLongArrayTag(long[] buf) throws IOException;
	
	long[] readLongArrayTag() throws IOException;
	
	long readLongTag() throws IOException;
	
	/**
	 * 
	 * @return the next tag
	 */
	NBT readNBT() throws IOException;
	
	/**
	 * Used in case of CompoundTag or EndTag for moving to the next element<br>
	 * for ListTag(CompounTag) and ListTag(ListTag) it will move in the next list or compound
	 * @throws IOException
	 */
	void readNextEntry() throws IOException;
	
	short readShortTag() throws IOException;
	
	String readStringTag() throws IOException;
	
	default NamespacedKey readNamespacedKey() throws IOException {
		return NamespacedKey.of(readStringTag());
	}

	/**
	 * Reads a IntArrayTag as UUID
	 * @return
	 * @throws IOException
	 */
	UUID readUUID() throws IOException;
	
	/**
	 * Skips the current Tag (for compound the whole compound will be skipped same for lists) 
	 * @throws IOException
	 */
	void skipTag() throws IOException;
	
	/**
	 * Reads until the end of the current component or list and skips over {@link TagType#TAG_END}
	 * @throws IOException
	 */
	void skipToEnd() throws IOException;
	
	/**
	 * Sets a marker at the current position for returning by {@link #reset()}
	 */
	void mark();
	
	/**
	 * Returns to the marker set by {@link #mark()}
	 */
	void reset();
	
	/**
	 * @throws IOException 
	 * @see #search(String, TagType, boolean)
	 */
	default void search(CharSequence key) throws IOException {
		search(key, null);
	}
	
	/**
	 * @throws IOException 
	 * @see #search(String, TagType, boolean)
	 */
	default void search(CharSequence key, TagType type) throws IOException {
		search(key, type, false);
	}
	
	/**
	 * Reads until it reaches the key and/or type or the end of the current compound or list<br>
	 * For using only one set the other one to null
	 * @param key the searched key
	 * @param type the searched {@link TagType}
	 * @param list if the type should be used as list of type (ignored if type is null)
	 * @throws IOException 
	 */
	void search(CharSequence key, TagType type, boolean list) throws IOException;
	
	/**
	 * Reads the current tag as number
	 * @return number
	 * @throws IOException
	 */
	Number readNumber() throws IOException;

	/**
	 * Reads a byte tag as boolean
	 * @return
	 * @throws IOException
	 */
	default boolean readBoolean() throws IOException {
		return readByteTag() == 1;
	}

}