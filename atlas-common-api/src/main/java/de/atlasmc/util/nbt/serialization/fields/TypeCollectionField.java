package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class TypeCollectionField<T, K> extends AbstractCollectionField<T, Collection<K>> {

	private final BiConsumer<T, K> set;
	private final NBTSerializationHandler<K> handler;
	
	public TypeCollectionField(CharSequence key, ToBooleanFunction<T> has, Function<T, Collection<K>> get, BiConsumer<T, K> set, NBTSerializationHandler<K> handler, boolean optional) {
		super(key, LIST, has, get, optional);
		this.handler = handler;
		this.set = set;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value)) {
			if (!useDefault)
				writer.writeListTag(key, TagType.COMPOUND, 0);
			return true;
		}
		final Collection<K> list = get.apply(value);
		final int size = list.size();
		if (size == 0) {
			if (!useDefault)
				writer.writeListTag(key, TagType.COMPOUND, 0);
			return true;
		}
		writer.writeListTag(key, TagType.COMPOUND, size);
		for (K v : list) {
			writer.writeCompoundTag();
			handler.serialize(v, writer, context);
			writer.writeEndTag();
		}
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
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
			K v = handler.deserialize(reader, context);
			set.accept(value, v);
		}
		reader.readNextEntry();
	}

}
