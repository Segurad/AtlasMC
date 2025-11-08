package de.atlasmc.nbt;

import java.util.function.Supplier;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.tag.ByteArrayTag;
import de.atlasmc.nbt.tag.ByteTag;
import de.atlasmc.nbt.tag.CompoundTag;
import de.atlasmc.nbt.tag.DoubleTag;
import de.atlasmc.nbt.tag.EndTag;
import de.atlasmc.nbt.tag.FloatTag;
import de.atlasmc.nbt.tag.IntArrayTag;
import de.atlasmc.nbt.tag.IntTag;
import de.atlasmc.nbt.tag.ListTag;
import de.atlasmc.nbt.tag.LongArrayTag;
import de.atlasmc.nbt.tag.LongTag;
import de.atlasmc.nbt.tag.NBT;
import de.atlasmc.nbt.tag.ShortTag;
import de.atlasmc.nbt.tag.StringTag;
import de.atlasmc.util.annotation.Nullable;

public enum TagType implements IDHolder {

	TAG_END(() -> { return EndTag.INSTANCE; }),
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
	
}
