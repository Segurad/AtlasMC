package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ToBooleanFunction;
import io.netty.buffer.ByteBuf;

public class CodecCollectionField<T, V> extends AbstractCollectionField<T, V> {

	private final StreamCodec<V> codec;
	
	public CodecCollectionField(ToBooleanFunction<T> has, Function<T, V> get, StreamCodec<V> codec) {
		super(has, get);
		this.codec = Objects.requireNonNull(codec);
	}

	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		V value = has.applyAsBoolean(type) ? get.apply(type) : null;
		codec.serialize(value, buf, context);
		return true;
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		V value = get.apply(type);
		codec.deserialize(value, buf, context);
	}

}
