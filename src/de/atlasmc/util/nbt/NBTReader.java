package de.atlasmc.util.nbt;

import java.io.IOException;

public interface NBTReader {
	
	public TagType getType();
	
	public TagType getListType();
	
	public int getRestPayload();
	
	public String getName();
	
	public byte readByteTag() throws IOException;
	
	public short readShortTag() throws IOException;
	
	public int readIntTag() throws IOException;
	
	public long readLongTag() throws IOException;
	
	public float readFloatTag() throws IOException;
	
	public double readDoubleTag() throws IOException;
	
	public byte[] readByteArrayTag() throws IOException;
	
	public String readStringTag() throws IOException;
	
	public void readListTag() throws IOException;
	
	public int[] readIntArrayTag() throws IOException;
	
	public long[] readLongArrayTag() throws IOException;
	
	public Object readTag() throws IOException;

}