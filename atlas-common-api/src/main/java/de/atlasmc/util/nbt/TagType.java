package de.atlasmc.util.nbt;

import java.util.List;
import java.util.function.Supplier;

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

	TAG_END(null),
	BYTE(ByteTag::new, true),
	SHORT(ShortTag::new, true),
	INT(IntTag::new, true),
	LONG(LongTag::new, true),
	FLOAT(FloatTag::new, true),
	DOUBLE(DoubleTag::new, true),
	BYTE_ARRAY(ByteArrayTag::new),
	STRING(StringTag::new),
	LIST(ListTag::new),
	COMPOUND(CompoundTag::new),
	INT_ARRAY(IntArrayTag::new),
	LONG_ARRAY(LongArrayTag::new);
	
	private static List<TagType> VALUES;
	
	private final boolean isNum;
	private final boolean isArray;
	private final Supplier<NBT> constructor;
	
	private TagType(Supplier<NBT> constructor) {
		this(constructor, false);
	}
	
	private TagType(Supplier<NBT> constructor, boolean num) {
		this(constructor, num, false);
	}
	
	private TagType(Supplier<NBT> constructor, boolean num, boolean array) {
		this.isNum = num;
		this.isArray = array;
		this.constructor = constructor;
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
	
	public boolean isArray() {
		return isArray;
	}

	public NBT createTag() {
		return constructor == null ? null : constructor.get();
	}

	public NBT createTag(Object field) {
		return createTag(null, field);
	}
	
	public NBT createTag(String name, Object field) {
		if (constructor == null)
			return null;
		NBT nbt = constructor.get();
		nbt.setName(name);
		nbt.setData(field);
		return nbt;
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
