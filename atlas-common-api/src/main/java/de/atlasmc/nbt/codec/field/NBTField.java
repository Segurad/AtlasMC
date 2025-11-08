package de.atlasmc.nbt.codec.field;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.map.key.CharKey;

public abstract class NBTField<T> {
	
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
	
	public NBTField(CharSequence key, List<TagType> types, boolean serverOnly) {
		this.key = CharKey.literal(key);
		if (Objects.requireNonNull(types).isEmpty())
			throw new IllegalArgumentException("Types can not be empty!");
		this.types = types;
		this.serverOnly = serverOnly;
	}
	
	public NBTField(NBTFieldBuilder<T, ?> builder) {
		this(builder.getKey(), builder.getTypes(), builder.isServerOnly());
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
