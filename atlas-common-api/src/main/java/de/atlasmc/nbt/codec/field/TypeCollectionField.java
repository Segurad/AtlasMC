package de.atlasmc.nbt.codec.field;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

public class TypeCollectionField<T, V> extends AbstractCollectionField<T, Collection<V>, NBTCodec<V>> {

	private final BiConsumer<T, V> setter;
	
	public TypeCollectionField(TypeCollectionFieldBuilder<T, V> builder) {
		super(builder);
		this.setter = Objects.requireNonNull(builder.getSetter());
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		if (hasData != null && !hasData.applyAsBoolean(value)) {
			return true;
		}
		final Collection<V> list = getter.apply(value);
		final int size = list.size();
		if (size == 0) {
			return true;
		}
		writer.writeListTag(key, TagType.COMPOUND, size);
		for (V v : list) {
			writer.writeCompoundTag();
			fieldType.serialize(v, writer, context);
			writer.writeEndTag();
		}
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return;
		}
		if (listType != TagType.COMPOUND)
			throw new NBTException("Expected list of type COMPOUND but was: " + listType);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			reader.readNextEntry();
			V v = fieldType.deserialize(reader, context);
			setter.accept(value, v);
		}
		reader.readNextEntry();
	}

}
