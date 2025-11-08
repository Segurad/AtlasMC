package de.atlasmc.nbt.codec.field;

import java.io.IOException;
import java.util.List;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

public class CodecListField<T, V> extends AbstractCollectionField<T, List<V>, NBTCodec<V>> {

	private final List<TagType> types;
	private final boolean optional;
	
	public CodecListField(CodecListFieldBuilder<T, V> builder) {
		super(builder);
		this.optional = builder.isOptional();
		this.types = fieldType.getTags();
	}

	@Override
	public boolean serialize(T type, NBTWriter writer, CodecContext context) throws IOException {
		if (hasData != null && !hasData.applyAsBoolean(type)) {
			if (!optional)
				writer.writeListTag(key, types.get(0), 0);
			return true;
		}
		final List<V> list = getter.apply(type);
		final int size = list.size();
		if (size == 0) { // check again because getter may return empty list in case no hasData function is set
			if (!optional)
				writer.writeListTag(key, types.get(0), 0);
			return true;
		}
		writer.writeListTag(key, types.get(0), size);
		for (int i = 0; i < size; i++) {
			V value = list.get(i);
			fieldType.serialize(null, value, writer, context);
		}
		return true;
	}

	@Override
	public void deserialize(T type, NBTReader reader, CodecContext context) throws IOException {
		final TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return;
		}
		if (!types.contains(listType))
			throw new NBTException("Unexpected list type: " + listType);
		final List<V> list = getter.apply(type);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			V value = fieldType.deserialize(null, reader, context);
			list.add(value);
		}
		reader.readNextEntry();
	}

}
