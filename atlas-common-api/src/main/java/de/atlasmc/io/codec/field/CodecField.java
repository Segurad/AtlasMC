package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class CodecField<T, V> extends AbstractObjectField<T, V> {

	private final StreamCodec<V> codec;
	
	public CodecField(Function<T, V> get, BiConsumer<T, V> set, StreamCodec<V> codec) {
		super(get, set);
		this.codec = Objects.requireNonNull(codec);
	}

	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		V value = get.apply(type);
		if (!codec.getType().isInstance(value))
			return false;
		return codec.serialize(value, buf, context);
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		V value = codec.deserialize(buf);
		set.accept(type, value);
	}

}
