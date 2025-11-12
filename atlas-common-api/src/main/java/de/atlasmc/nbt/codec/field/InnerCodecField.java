package de.atlasmc.nbt.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

public class InnerCodecField<T, K> extends NBTField<T> {

	private final Function<T, ? super K> get;
	private final NBTCodec<K> codec;
	
	public InnerCodecField(CharSequence key, Function<T, ? super K> get, NBTCodec<K> codec, boolean serverOnly) {
		super(key, codec.getTags(), serverOnly);
		this.codec = Objects.requireNonNull(codec);
		this.get = Objects.requireNonNull(get);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		Object raw = get.apply(value);
		if (!codec.getType().isInstance(raw))
			return false;
		@SuppressWarnings("unchecked")
		K v = (K) raw;
		codec.serialize(key, v, writer, context);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		Object raw = get.apply(value);
		if (!codec.getType().isInstance(raw))
			throw new NBTException("Invalid type: " + raw.getClass() + " for handler type: " + codec.getType());
		@SuppressWarnings("unchecked")
		K v = (K) raw;
		codec.deserialize(v, reader, context);
	}
}
