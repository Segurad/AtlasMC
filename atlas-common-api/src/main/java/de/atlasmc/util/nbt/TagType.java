package de.atlasmc.util.nbt;

import java.util.List;
import java.util.function.Supplier;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.nbt.tag.ByteArrayTag;
import de.atlasmc.util.nbt.tag.ByteTag;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.util.nbt.tag.DoubleTag;
import de.atlasmc.util.nbt.tag.EndTag;
import de.atlasmc.util.nbt.tag.FloatTag;
import de.atlasmc.util.nbt.tag.IntArrayTag;
import de.atlasmc.util.nbt.tag.IntTag;
import de.atlasmc.util.nbt.tag.ListTag;
import de.atlasmc.util.nbt.tag.LongArrayTag;
import de.atlasmc.util.nbt.tag.LongTag;
import de.atlasmc.util.nbt.tag.NBT;
import de.atlasmc.util.nbt.tag.ShortTag;
import de.atlasmc.util.nbt.tag.StringTag;

public enum TagType implements EnumID, EnumValueCache {

	TAG_END(null),
	BYTE(ByteTag::new, true),
	SHORT(ShortTag::new, true),
	INT(IntTag::new, true),
	LONG(LongTag::new, true),
	FLOAT(FloatTag::new, true),
	DOUBLE(DoubleTag::new, true),
	BYTE_ARRAY(ByteArrayTag::new, false, true),
	STRING(StringTag::new),
	LIST(ListTag::new),
	COMPOUND(CompoundTag::new),
	INT_ARRAY(IntArrayTag::new, false, true),
	LONG_ARRAY(LongArrayTag::new, false, true);
	
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
	
	@Override
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
	
	/**
	 * Returns weather or not this TagType is a array tag or not.<br>
	 * Number tags: {@link #BYTE_ARRAY}, {@link #INT_ARRAY}, {@link #LONG_ARRAY}
	 * @return true if this TagType is a number tag
	 */
	public boolean isArray() {
		return isArray;
	}

	/**
	 * Creates a NBT Object of the type.
	 * {@link EndTag} will return null.
	 * @return NBT or null
	 */
	@Nullable
	public NBT createTag() {
		return constructor == null ? null : constructor.get();
	}

	/**
	 * Creates a NBT Object of the given type with the given value.
	 * {@link EndTag} will return null.
	 * @param value to set
	 * @return NBT or null
	 */
	@Nullable
	public NBT createTag(Object value) {
		return createTag(null, value);
	}
	
	/**
	 * Creates a NBT Object of the given type with the given value.
	 * {@link EndTag} will return null.
	 * @param name to set
	 * @param value to set
	 * @return NBT or null
	 */
	@Nullable
	public NBT createTag(String name, Object value) {
		if (constructor == null)
			return null;
		NBT nbt = constructor.get();
		nbt.setName(name);
		nbt.setData(value);
		return nbt;
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	@NotNull
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
