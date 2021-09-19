package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.util.nbt.ListTag;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.TagType;

public interface NBTReader {
	
	public int getDepth();
	
	public String getFieldName();
	
	/**
	 * Returns the {@link TagType} of highest list contents or null
	 * @return tag type or null
	 */
	public TagType getListType();
	
	/**
	 * Returns the number of elements remaining in a {@link ListTag}
	 * @return number of elements
	 */
	public int getRestPayload();
	
	/**
	 * Returns the type of the current Tag
	 * @return tag type
	 */
	public TagType getType();
	
	public byte[] readByteArrayTag() throws IOException;
	
	public byte readByteTag() throws IOException;
	
	public double readDoubleTag() throws IOException;
	
	public float readFloatTag() throws IOException;
	
	public int[] readIntArrayTag() throws IOException;
	
	public int readIntTag() throws IOException;
	
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
	public void skipNBT() throws IOException;

}