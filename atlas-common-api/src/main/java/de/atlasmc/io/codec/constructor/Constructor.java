package de.atlasmc.io.codec.constructor;

import java.io.IOException;

import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public interface Constructor<T> {
	
	public T construct(ByteBuf in, CodecContext context) throws IOException;
	
	public void serialize(T value, ByteBuf out, CodecContext context) throws IOException;

}
