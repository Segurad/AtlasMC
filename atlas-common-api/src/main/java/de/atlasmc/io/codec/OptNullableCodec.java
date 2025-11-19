package de.atlasmc.io.codec;

import java.io.IOException;
import java.util.Objects;

import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

final class OptNullableCodec<T> implements StreamCodec<T> {

	private final StreamCodec<T> codec;
	
	public OptNullableCodec(StreamCodec<T> codec) {
		this.codec = Objects.requireNonNull(codec);
	}
	
	@Override
	public Class<?> getType() {
		return codec.getType();
	}

	@Override
	public boolean serialize(T value, ByteBuf output, CodecContext context) throws IOException {
		if (value == null) {
			output.writeBoolean(false);
			return true;
		}
		output.writeBoolean(true);
		return codec.serialize(value, output, context);
	}

	@Override
	public T deserialize(T value, ByteBuf input, CodecContext context) throws IOException {
		if (!input.readBoolean())
			return null;
		return codec.deserialize(value, input, context);
	}

}
