package de.atlasmc.node.entity.metadata.type;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class StreamCodecMetaType<T> extends MetaDataType<T> {
	
	private final StreamCodec<T> codec;
	private final Function<T, T> copy;

	public StreamCodecMetaType(int type, boolean optional, StreamCodec<T> codec, Function<T, T> copy) {
		super(type, codec.getType(), optional);
		this.codec = Objects.requireNonNull(codec);
		this.copy = copy;
	}

	@Override
	public T read(ByteBuf in, CodecContext context) throws IOException {
		return codec.deserialize(in, CodecContext.DEFAULT_CLIENT);
	}

	@Override
	public void write(T data, ByteBuf out, CodecContext context) throws IOException {
		codec.serialize(data, out);
	}
	
	@Override
	public T copyData(T data) {
		if (copy == null)
			return super.copyData(data);
		return copy.apply(data);
	}

}
