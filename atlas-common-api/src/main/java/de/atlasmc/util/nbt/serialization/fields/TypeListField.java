package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class TypeListField<T, K extends NBTSerializable> extends NBTField<T> {

	private final Function<T, List<K>> listSupplier;
	private final NBTSerializationHandler<K> handler;
	
	public TypeListField(CharSequence key, Function<T, List<K>> listSupplier, NBTSerializationHandler<K> handler, boolean optional) {
		super(key, TagType.LIST, optional);
		this.listSupplier = listSupplier;
		this.handler = handler;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		final List<K> list = listSupplier.apply(value);
		final int size = list.size();
		if (size == 0 && useDefault)
			return true;
		writer.writeListTag(key, TagType.COMPOUND, size);
		for (int i = 0; i < size; i++) {
			writer.writeCompoundTag();
			K v = list.get(i);
			handler.serialize(v, writer, context);
			writer.writeEndTag();
		}
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		TagType listType = reader.getListType();
		if (listType == TagType.TAG_END)
			return;
		if (listType != TagType.COMPOUND)
			throw new NBTException("Expected list of type COMPOUND but was: " + listType);
		final List<K> list = listSupplier.apply(value);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			reader.readNextEntry();
			K v = handler.deserialize(reader, context);
			list.add(v);
		}
		reader.readNextEntry();
	}

}
