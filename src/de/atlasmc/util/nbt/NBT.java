package de.atlasmc.util.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface NBT {
	
	public String getName();
	public Object getData();
	public TagType getType();
	public void read(DataInput input, boolean readName) throws IOException;
	public void write(DataOutput output, boolean readName) throws IOException;
	
	public static ByteTag createByteTag(String name, byte data) {
		return new ByteTag(name, data);
	}
	
	public static ShortTag createShortTag(String name, short data) {
		return new ShortTag(name, data);
	}
	
	public static IntTag createIntTag(String name, int data) {
		return new IntTag(name, data);
	}
	
	public static LongTag createLongTag(String name, long data) {
		return new LongTag(name, data);
	}
	
	public static FloatTag createFloatTag(String name, float data) {
		return new FloatTag(name, data);
	}
	
	public static DoubleTag createDoubleTag(String name, double data) {
		return new DoubleTag(name, data);
	}
	
	public static ByteArrayTag createByteArrayTag(String name, byte[] data) {
		return new ByteArrayTag(name, data);
	}
	
	public static StringTag createStringTag(String name, String data) {
		return new StringTag(name, data);
	}
	
	public static ListTag<? extends NBT> createListTag(String name, TagType type) {
		return new ListTag<>(name, type);
	}
	
	public static CompoundTag createCompoundTag(String name) {
		return new CompoundTag(name);
	}
	
	public static IntArrayTag createIntArrayTag(String name, int[] data) {
		return new IntArrayTag(name, data);
	}
	
	public static LongArrayTag createIntArrayTag(String name, long[] data) {
		return new LongArrayTag(name, data);
	}
}
