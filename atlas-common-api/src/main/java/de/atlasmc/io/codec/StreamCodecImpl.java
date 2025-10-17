package de.atlasmc.io.codec;

import java.io.IOException;

import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class StreamCodecImpl<T> implements StreamCodec<T> {

	@Override
	public boolean serialize(T value, ByteBuf ouput, CodecContext context) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T deserialize(T value, ByteBuf input, CodecContext context) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CodecContext getDefaultContext() {
		return CodecContext.DEFAULT_SERVER;
	}

}
