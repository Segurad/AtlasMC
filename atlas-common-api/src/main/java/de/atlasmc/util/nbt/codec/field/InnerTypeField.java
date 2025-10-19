package de.atlasmc.util.nbt.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.codec.type.FieldType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class InnerTypeField<T, K> extends NBTField<T> {

	private final Function<T, ? super K> get;
	private final NBTCodec<K> codec;
	
	public InnerTypeField(CharSequence key, Function<T, ? super K> get, NBTCodec<K> handler, boolean serverOnly) {
		super(key, FieldType.COMPOUND, serverOnly);
		this.codec = Objects.requireNonNull(handler);
		this.get = Objects.requireNonNull(get);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		Object raw = get.apply(value);
		if (!codec.getType().isInstance(raw))
			return false;
		@SuppressWarnings("unchecked")
		K v = (K) raw;
		writer.writeCompoundTag(key);
		codec.serialize(v, writer, context);
		writer.writeEndTag();
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		Object raw = get.apply(value);
		if (!codec.getType().isInstance(raw))
			throw new NBTException("Invalid type: " + raw.getClass() + " for handler type: " + codec.getType());
		@SuppressWarnings("unchecked")
		K v = (K) raw;
		reader.readNextEntry();
		codec.deserialize(v, reader, context);
	}
}
