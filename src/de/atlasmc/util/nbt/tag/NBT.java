package de.atlasmc.util.nbt.tag;

import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.TagType;

public interface NBT extends Cloneable, NBTHolder {
	
	/**
	 * Returns the name key of this NBT
	 * @return name key
	 */
	public String getName();
	
	/**
	 * Sets the name key of this NBT
	 * @param name key that should be set
	 */
	public void setName(String name);
	
	public Object getData();
	
	public void setData(Object data);
	
	/**
	 * Returns the type of this NBT
	 * @return type
	 */
	public TagType getType();
	
	public static ByteTag createByteTag(String name, int data) {
		return new ByteTag(name, (byte) data);
	}
	
	public static ShortTag createShortTag(String name, int data) {
		return new ShortTag(name, (short) data);
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
	
	public static LongArrayTag createLongArrayTag(String name, long[] data) {
		return new LongArrayTag(name, data);
	}
	
	public NBT clone();

	public static ListTag<? extends NBT> createListTag(String string, TagType type, int payloadsize) {
		return new ListTag<>(string, type, payloadsize);
	}
	
}
