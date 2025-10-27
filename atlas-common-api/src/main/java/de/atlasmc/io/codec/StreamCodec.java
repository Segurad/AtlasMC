package de.atlasmc.io.codec;

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
	
}
