package de.atlasmc.util.nbt.codec.type;

import java.util.List;

import de.atlasmc.util.nbt.TagType;

public abstract class FieldType {
	
	public static final List<TagType>
	BYTE = List.of(TagType.BYTE),
	SHORT = List.of(TagType.SHORT),
	INT = List.of(TagType.INT),
	LONG = List.of(TagType.LONG),
	FLOAT = List.of(TagType.FLOAT),
	DOUBLE = List.of(TagType.DOUBLE),
	BYTE_ARRAY = List.of(TagType.BYTE_ARRAY),
	STRING = List.of(TagType.STRING),
	LIST = List.of(TagType.LIST),
	COMPOUND = List.of(TagType.COMPOUND),
	INT_ARRAY = List.of(TagType.INT_ARRAY),
	LONG_ARRAY = List.of(TagType.LONG_ARRAY),
	LIST_STRING = List.of(TagType.LIST, TagType.STRING),
	INT_LIST = List.of(TagType.INT, TagType.LIST),
	INT_ARRAY_STRING = List.of(TagType.INT_ARRAY, TagType.STRING);
	
	public abstract List<TagType> getTypes();

}
