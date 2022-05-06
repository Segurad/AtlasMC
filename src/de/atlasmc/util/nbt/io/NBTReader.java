package de.atlasmc.util.nbt.io;

import java.io.Closeable;
import java.io.IOException;
import java.util.UUID;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.ListTag;
import de.atlasmc.util.nbt.tag.NBT;

/**
 * Reader for NBT Data<br>
 * <h1>Usage Examples</h1><br>
 * Read {@link TagType#LIST}
 * <pre>
 * reader.readNextEntry(); // skip to first element of list
 * while (reader.getRestPayload() > 0) {
 * 	// read element
 * }
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
 * </pre>
 */
public interface NBTReader extends Closeable {
	
	/**
	 * Returns the Depth in the current NBT Structure
	 * @return
	 */
	public int getDepth();
	
	/**
	 * Returns the current fields name as {@link CharSequence} or null
	 * @return sequence or null
	 */
	public CharSequence getFieldName();
	
	/**
	 * Returns the {@link TagType} of highest list contents or null
	 * @return tag type or null
	 */
	public TagType getListType();
	
	/**
	 * Returns the number of elements remaining in a {@link ListTag}.<br>
	 * May be 1 for more than one element in some implementations.
	 * @return number of elements
	 */
	public int getRestPayload();
	
	/**
	 * Returns the type of the current Tag
	 * @return tag type
	 */
	public TagType getType();
	
	public void readByteArrayTag(IntConsumer dataConsumer) throws IOException;
	
	public byte[] readByteArrayTag() throws IOException;
	
	public byte readByteTag() throws IOException;
	
	public double readDoubleTag() throws IOException;
	
	public float readFloatTag() throws IOException;
	
	public void readIntArrayTag(IntConsumer dataConsumer) throws IOException;
	
	public int[] readIntArrayTag() throws IOException;
	
	public int readIntTag() throws IOException;
	
	public void readLongArrayTag(LongConsumer dataConsumer) throws IOException;
	
	public long[] readLongArrayTag() throws IOException;
	
	public long readLongTag() throws IOException;
	
	/**
	 * 
	 * @return the next tag
	 */
	public NBT readNBT() throws IOException;
	
	/**
	 * Used in case of CompoundTag or EndTag for moving to the next element<br>
	 * for ListTag(CompounTag) and ListTag(ListTag) it will move in the next list or compound
	 * @throws IOException
	 */
	public void readNextEntry() throws IOException;
	
	public short readShortTag() throws IOException;
	
	public String readStringTag() throws IOException;

	/**
	 * Reads a IntArrayTag as UUID
	 * @return
	 * @throws IOException
	 */
	public UUID readUUID() throws IOException;
	
	/**
	 * Skips the current Tag (for compound the whole compound will be skipped same for lists) 
	 * @throws IOException
	 */
	public void skipTag() throws IOException;
	
	/**
	 * Sets a marker at the current position for returning by {@link #reset()}
	 */
	public void mark();
	
	/**
	 * Returns to the marker set by {@link #mark()}
	 */
	public void reset();
	
	/**
	 * @throws IOException 
	 * @see #search(String, TagType, boolean)
	 */
	public default void search(CharSequence key) throws IOException {
		search(key, null);
	}
	
	/**
	 * @throws IOException 
	 * @see #search(String, TagType, boolean)
	 */
	public default void search(CharSequence key, TagType type) throws IOException {
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
	public void search(CharSequence key, TagType type, boolean list) throws IOException;

}