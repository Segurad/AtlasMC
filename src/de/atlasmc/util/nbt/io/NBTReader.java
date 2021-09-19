package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.util.nbt.ListTag;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.TagType;

public interface NBTReader {
	
	public int getDepth();
	
	public String getFieldName();
	
	public TagType getListType();
	
	/**
	 * Returns the number of elements remaining in a {@link ListTag}
	 * @return number of elements
	 */
	public int getRestPayload();
	
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
	 * Used in case of CompoundTag or EndTag
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
	
	public void skipNBT() throws IOException;

}