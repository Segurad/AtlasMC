package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class CompoundTypeField<T, K extends NBTSerializable> extends NBTField<T> {
	
	private final Function<T, ? super K> get;
	private final BiConsumer<T, ? super K> set;
	private final NBTSerializationHandler<K> handler;
	
	public CompoundTypeField(CharSequence key, Function<T, ? super K> get, BiConsumer<T, ? super K> set, NBTSerializationHandler<K> handler) {
		super(key, TagType.COMPOUND, true);
		this.handler = handler;
		this.set = set;
		this.get = get;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		Object raw = get.apply(value);
		if (!handler.getType().isInstance(raw))
			return false;
		@SuppressWarnings("unchecked")
		K v = (K) raw;
		writer.writeCompoundTag(key);
		handler.serialize(v, writer, context);
		writer.writeEndTag();
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		reader.readNextEntry();
		K v = handler.deserialize(reader);
		set.accept(value, v);
	}

}
