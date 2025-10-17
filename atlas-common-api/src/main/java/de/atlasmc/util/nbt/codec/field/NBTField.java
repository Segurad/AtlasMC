package de.atlasmc.util.nbt.codec.field;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

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
	
	/**
	 * The key used for this field
	 */
	public final CharKey key;
	/**
	 * Immutable list containing all {@link TagType}s valid for this field
	 */
	public final List<TagType> types;
	/**
	 * Whether or not this field should only be serialized when send to client
	 */
	public final boolean serverOnly;
	
	public NBTField(NBTFieldBuilder<T, ?> builder) {
		this.key = CharKey.literal(builder.getKey());
		this.types = builder.getTypes();
		this.serverOnly = builder.isServerOnly();
	}
	
	/**
	 * Serializes a field of the given type
	 * @param type to serialize from
	 * @param writer to write to
	 * @param context used in this operation
	 * @return true of field was handled successfully
	 * @throws IOException
	 */
	public abstract boolean serialize(T type, NBTWriter writer, CodecContext context) throws IOException;
	
	/**
	 * Deserializes the field for the given type
	 * @param type to deserialize for
	 * @param reader to read from
	 * @param context used in this operation
	 * @throws IOException
	 */
	public abstract void deserialize(T type, NBTReader reader, CodecContext context) throws IOException;
	
}
