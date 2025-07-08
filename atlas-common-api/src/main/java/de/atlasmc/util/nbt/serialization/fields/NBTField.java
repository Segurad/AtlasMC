package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public abstract class NBTField<T> {
	
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
	LIST_STRING = List.of(TagType.LIST, TagType.STRING);
	
	public final CharKey key;
	public final List<TagType> types;
	public final boolean useDefault;
	
	public NBTField(CharSequence key, List<TagType> types, boolean useDefault) {
		this.key = CharKey.literal(key);
		this.types = types;
		this.useDefault = useDefault;
	}
	
	/**
	 * Serializes a field of the given value
	 * @param value to serialize from
	 * @param writer to write to
	 * @param context
	 * @return true of field was handled successfully
	 * @throws IOException
	 */
	public abstract boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException;
	
	public abstract void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException;
	
}
