package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.ObjLongConsumer;
import java.util.function.ToLongFunction;

import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class LongField<T> extends StreamField<T> {

	private final ToLongFunction<T> get;
	private final ObjLongConsumer<T> set;
	
	public LongField(ToLongFunction<T> get, ObjLongConsumer<T> set) {
		this.get = Objects.requireNonNull(get);
		this.set = Objects.requireNonNull(set);
	}
	
	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		buf.writeLong(get.applyAsLong(type));
		return true;
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		set.accept(type, buf.readLong());
	}

}
