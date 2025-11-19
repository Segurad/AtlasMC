package de.atlasmc.io.codec;

import java.io.IOException;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

final class VarIntToObjectCodec<T> implements StreamCodec<T> {

	private final Class<?> clazz;
	private final ToIntFunction<T> toInt;
	private final IntFunction<T> toObject;
	
	public VarIntToObjectCodec(Class<T> clazz, IntFunction<T> toObject, ToIntFunction<T> toInt) {
		this.clazz = Objects.requireNonNull(clazz);
		this.toObject = Objects.requireNonNull(toObject);
		this.toInt = Objects.requireNonNull(toInt);
	}
	
	@Override
	public Class<?> getType() {
		return clazz;
	}

	@Override
	public boolean serialize(T value, ByteBuf output, CodecContext context) throws IOException {
		output.writeInt(toInt.applyAsInt(value));
		return true;
	}

	@Override
	public T deserialize(T value, ByteBuf input, CodecContext context) throws IOException {
		return toObject.apply(input.readInt());
	}

}
