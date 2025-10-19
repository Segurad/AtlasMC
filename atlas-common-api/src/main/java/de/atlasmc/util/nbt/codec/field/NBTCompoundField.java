package de.atlasmc.util.nbt.codec.field;

import java.io.IOException;
import java.util.Map;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class NBTCompoundField<T> extends NBTField<T> {
	
	private final int count;
	private final NBTField<T>[] fields;
	private final byte[] skips;
	private final Map<CharKey, NBTField<T>>[] typeFields;
	private final ToBooleanFunction<T> has;

	public NBTCompoundField(NBTCompoundFieldBuilder<T> builder) {
		super(builder);
		this.has = builder.has;
		this.fields = builder.buildFieldsArray();
		this.count = fields.length;
		this.typeFields = builder.buildTypeFieldsArray();
		this.skips = builder.buildSkipArray();
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value))
			return true;
		for (int i = 0; i < count; i++) {
			final NBTField<T> field = fields[i];
			field.serialize(value, writer, context);
			byte skip = skips[i];
			i += skip;
		}
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		reader.readNextEntry();
		TagType type = null;
		while ((type = reader.getType()) != TagType.TAG_END) {
			final Map<CharKey, NBTField<T>> fields = typeFields[type.getID() - 1];
			if (fields == null) {
				reader.skipTag();
				continue;
			}
			final NBTField<T> field = fields.get(reader.getFieldName());
			if (field == null) {
				reader.skipTag();
				continue;
			}
			field.deserialize(value, reader, context);
		}
		reader.readNextEntry();
	}

}
