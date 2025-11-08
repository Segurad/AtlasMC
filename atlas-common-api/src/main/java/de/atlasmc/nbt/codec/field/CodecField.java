package de.atlasmc.nbt.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

public class CodecField<T, V> extends NBTField<T>{

	protected final Function<T, V> getter;
	protected final NBTCodec<V> codec;
	private final BiConsumer<T, V> setter;
	private final V defaultValue;
	
	public CodecField(CodecFieldBuilder<T, V> builder) {
		super(builder);
		this.getter = Objects.requireNonNull(builder.getGetter());
		this.codec = Objects.requireNonNull(builder.getCodec());
		this.setter = Objects.requireNonNull(builder.getSetter());
		this.defaultValue = builder.getDefaultValue();
	}

	@Override
	public boolean serialize(T type, NBTWriter writer, CodecContext context) throws IOException {
		if (serverOnly && context.clientSide)
			return true;
		final V value = getter.apply(type);
		if (value == null || value.equals(defaultValue))
			return true;
		final NBTCodec<V> codec = this.codec;
		if (!codec.getType().isInstance(value))
			return false;
		if (codec.isField())
			return codec.serialize(key, value, writer, context);
		writer.writeCompoundTag(key);
		codec.serialize(key, value, writer, context);
		writer.writeEndTag();
		return true;
	}

	@Override
	public void deserialize(T type, NBTReader reader, CodecContext context) throws IOException {
		if (serverOnly && context.clientSide)
			return; // do not deserialize fields send by client that are server only
		final NBTCodec<V> codec = this.codec;
		if (codec.isField()) {
			setter.accept(type, codec.deserialize(codec.isReuseValue() ? getter.apply(type) : null, reader, context));
		} else {
			reader.readNextEntry();
			setter.accept(type, codec.deserialize(codec.isReuseValue() ? getter.apply(type) : null, reader, context));
		}
	}

}
