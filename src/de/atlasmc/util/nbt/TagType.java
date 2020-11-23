package de.atlasmc.util.nbt;

public enum TagType {

	TAG_END(0),
	BYTE(1),
	SHORT(2),
	INT(3),
	LONG(4),
	FLOAT(5),
	DOUBLE(6),
	BYTE_ARRAY(7),
	STRING(8),
	LIST(9),
	COMPOUND(10),
	INT_ARRAY(11),
	LONG_ARRAY(12);
	
	private int id;
	
	private TagType(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public static TagType getByID(int id) {
		if (id < 1 || id > 12) throw new IllegalArgumentException("ID (" + id + ") must be between 1 and 12");
		TagType[] types = values();
		return types[id-1];
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
			return new ListTag<NBT>();
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
}
