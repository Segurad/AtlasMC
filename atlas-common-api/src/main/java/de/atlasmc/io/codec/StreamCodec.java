package de.atlasmc.io.codec;

import de.atlasmc.util.codec.Codec;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public interface StreamCodec<T> extends Codec<T, ByteBuf, ByteBuf, CodecContext> {

}
