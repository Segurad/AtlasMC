package de.atlasmc.io.codec;

import java.io.IOException;

import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public interface StreamSerializable {

	StreamCodec<? extends StreamSerializable> getStreamCodec();
	
	default void writeToStream(ByteBuf buf, CodecContext context) throws IOException {
		@SuppressWarnings("unchecked")
		StreamCodec<StreamSerializable> codec = (StreamCodec<StreamSerializable>) getStreamCodec();
		codec.serialize(this, buf, context);
	}
	
}
