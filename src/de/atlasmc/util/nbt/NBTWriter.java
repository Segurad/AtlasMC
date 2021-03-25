package de.atlasmc.util.nbt;

import java.io.IOException;

public interface NBTWriter {
	
	public void writeEndTag() throws IOException;
	
	public void writeByteTag(String name, int value) throws IOException;
	
	public void writeShortTag(String name, int value) throws IOException;
	
	public void writeIntTag(String name, int value) throws IOException;
	
	public void writeLongTag(String name, long value) throws IOException;
	
	public void writeFloatTag(String name, float value) throws IOException;
	
	public void writeDoubleTag(String name, double value) throws IOException;
	
	public void writeByteArrayTag(String name, byte[] data) throws IOException;
	
	public void writeStringTag(String name, String value) throws IOException;
	
	public void writeListTag(String name, TagType payloadType, int payloadsize) throws IOException;
	
	public void writeCompoundTag(String name) throws IOException;
	
	public void writeIntArrayTag(String name, int[] data) throws IOException;
	
	public void writeLongArrayTag(String name, long[] data) throws IOException;
	
	public void writeNBT(NBT nbt) throws IOException;
	
}
