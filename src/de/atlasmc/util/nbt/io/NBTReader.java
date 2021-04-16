package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.TagType;

public interface NBTReader {
	
	public TagType getType();
	
	public TagType getListType();
	
	public int getRestPayload();
	
	public String getFieldName();
	
	public byte readByteTag() throws IOException;
	
	public short readShortTag() throws IOException;
	
	public int readIntTag() throws IOException;
	
	public long readLongTag() throws IOException;
	
	public float readFloatTag() throws IOException;
	
	public double readDoubleTag() throws IOException;
	
	public byte[] readByteArrayTag() throws IOException;
	
	public String readStringTag() throws IOException;
	
	/**
	 * Used in case of CompoundTag or EndTag
	 * @throws IOException
	 */
	public void readNextEntry() throws IOException;
	
	public int[] readIntArrayTag() throws IOException;
	
	/**
	 * Reads a IntArrayTag as UUID
	 * @return
	 * @throws IOException
	 */
	public UUID readUUID() throws IOException;
	
	public long[] readLongArrayTag() throws IOException;
	
	/**
	 * 
	 * @return the next tag
	 */
	public NBT readNBT() throws IOException;

	public int getDepth();

}