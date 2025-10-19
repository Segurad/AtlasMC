package de.atlasmc.util.nbt.tag;

import java.io.IOException;

import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public interface NBT extends Cloneable, NBTHolder {
	
	/**
	 * Returns the name key of this NBT
	 * @return name key
	 */
	String getName();
	
	/**
	 * Sets the name key of this NBT
	 * @param name key that should be set
	 */
	void setName(String name);
	
	Object getData();
	
	void setData(Object data);
	
	/**
	 * Returns the type of this NBT
	 * @return type
	 */
	TagType getType();
	
	NBT clone();
	
	default void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		toNBT(getName(), writer, systemData);
	}
	
	/**
	 * Write the NBT of the Holder
	 * Does not create a new CompoundTag
	 * @param name the name used instead {@link #getName()}
	 * @param writer
	 * @param systemData true if it is used system internal false while send to client
	 * @throws IOException
	 */
	void toNBT(CharSequence name, NBTWriter writer, boolean systemData) throws IOException;
	
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
	
	public static ListTag createListTag(String name, TagType type) {
		return new ListTag(name, type);
	}
	
	public static CompoundTag createCompoundTag(String name) {
		return new CompoundTag(name);
	}
	
	public static HoldingCompoundTag createCompoundTag(String name, NBTHolder holder) {
		return new HoldingCompoundTag(name, holder);
	}
	
	public static IntArrayTag createIntArrayTag(String name, int[] data) {
		return new IntArrayTag(name, data);
	}
	
	public static LongArrayTag createLongArrayTag(String name, long[] data) {
		return new LongArrayTag(name, data);
	}

	public static ListTag createListTag(String string, TagType type, int payloadsize) {
		return new ListTag(string, type, payloadsize);
	}
	
}
