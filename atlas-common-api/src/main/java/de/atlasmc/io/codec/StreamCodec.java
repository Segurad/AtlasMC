package de.atlasmc.io.codec;

import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

import de.atlasmc.util.codec.Codec;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public interface StreamCodec<T> extends Codec<T, ByteBuf, ByteBuf, CodecContext> {

	public static <T> StreamCodecBuilder<T> builder(Class<T> clazz) {
		return new StreamCodecBuilder<>(clazz);
	}
	
	@Override
	default CodecContext getDefaultContext() {
		return CodecContext.DEFAULT_SERVER;
	}
	
	static <T> StreamCodec<T> optNullable(StreamCodec<T> codec) {
		return new OptNullableCodec<>(codec);
	}
	
	static <T> StreamCodec<T> varIntToObject(Class<T> clazz, IntFunction<T> toObject, ToIntFunction<T> toInt) {
		return new VarIntToObjectCodec<>(clazz, toObject, toInt);
	}
	
	static <T> StreamCodec<T> intToObject(Class<T> clazz, IntFunction<T> toObject, ToIntFunction<T> toInt) {
		return new IntToObjectCodec<>(clazz, toObject, toInt);
	}
	
}
