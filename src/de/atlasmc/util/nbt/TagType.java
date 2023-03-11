package de.atlasmc.util.nbt;

import java.util.List;

import de.atlasmc.util.nbt.tag.ByteArrayTag;
import de.atlasmc.util.nbt.tag.ByteTag;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.util.nbt.tag.DoubleTag;
import de.atlasmc.util.nbt.tag.FloatTag;
import de.atlasmc.util.nbt.tag.IntArrayTag;
import de.atlasmc.util.nbt.tag.IntTag;
import de.atlasmc.util.nbt.tag.ListTag;
import de.atlasmc.util.nbt.tag.LongArrayTag;
import de.atlasmc.util.nbt.tag.LongTag;
import de.atlasmc.util.nbt.tag.NBT;
import de.atlasmc.util.nbt.tag.ShortTag;
import de.atlasmc.util.nbt.tag.StringTag;

public enum TagType {

	TAG_END,
	BYTE(true),
	SHORT(true),
	INT(true),
	LONG(true),
	FLOAT(true),
	DOUBLE(true),
	BYTE_ARRAY,
	STRING,
	LIST,
	COMPOUND,
	INT_ARRAY,
	LONG_ARRAY;
	
	private static List<TagType> VALUES;
	
	private boolean isNum;
	
	private TagType() {
		this(false);
	}
	
	private TagType(boolean num) {
		this.isNum = num;
	}
	
	public int getID() {
		return ordinal();
	}
	
	public static TagType getByID(int id) {
		if (id < 0 || id > 12) 
			throw new IllegalArgumentException("ID (" + id + ") must be between 0 and 12");
		return getValues().get(id);
	}
	
	/**
	 * Returns weather or not this TagType is a number tag or not.<br>
	 * Number tags: {@link #BYTE}, {@link #SHORT}, {@link #INT}, {@link #LONG}, {@link #FLOAT}, {@link #DOUBLE}
	 * @return true if this TagType is a number tag
	 */
	public boolean isNumber() {
		return isNum;
	}

	public NBT createTag() {
		switch(this) {
		case BYTE:
			return new ByteTag();
		case BYTE_ARRAY:
			return new ByteArrayTag();
		case COMPOUND:
			return new CompoundTag();
		case DOUBLE:
			return new DoubleTag();
		case FLOAT:
			return new FloatTag();
		case INT:
			return new IntTag();
		case INT_ARRAY:
			return new IntArrayTag();
		case LIST:
			return new ListTag<>();
		case LONG:
			return new LongTag();
		case LONG_ARRAY:
			return new LongArrayTag();
		case SHORT:
			return new ShortTag();
		case STRING:
			return new StringTag();
		default:
			return null;
		}
	}

	public NBT createTag(Object field) {
		return createTag(null, field);
	}
	
	public NBT createTag(String name, Object field) {
		switch (this) {
		case BYTE:
			return new ByteTag(name, (byte) field);
		case BYTE_ARRAY:
			return new ByteArrayTag(name, (byte[]) field);
		case COMPOUND:
			return new CompoundTag(name);
		case DOUBLE:
			return new DoubleTag(name, (double) field);
		case FLOAT:
			return new FloatTag(name, (float) field);
		case INT:
			return new IntTag(name, (int) field);
		case INT_ARRAY:
			return new IntArrayTag(name, (int[]) field);
		case LIST:
			return new ListTag<>(name, (TagType) field);
		case LONG:
			return new LongTag(name, (long) field);
		case LONG_ARRAY:
			return new LongArrayTag(name, (long[]) field);
		case SHORT:
			return new ShortTag(name, (short) field);
		case STRING:
			return new StringTag(name, (String) field);
		default:
			return null;
		}
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<TagType> getValues() {
		if (VALUES == null)
			VALUES = List.of(values());
		return VALUES;
	}
	
	/**
	 * Releases the system resources used from the values cache
	 */
	public static void freeValues() {
		VALUES = null;
	}
	
}
