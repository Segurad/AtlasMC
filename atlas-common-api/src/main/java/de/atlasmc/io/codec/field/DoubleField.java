package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ToDoubleFunction;

import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class DoubleField<T> extends StreamField<T> {

	private final ToDoubleFunction<T> get;
	private final ObjDoubleConsumer<T> set;
	
	public DoubleField(ToDoubleFunction<T> get, ObjDoubleConsumer<T> set) {
		this.get = Objects.requireNonNull(get);
		this.set = Objects.requireNonNull(set);
	}
	
	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		buf.writeDouble(get.applyAsDouble(type));
		return true;
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		set.accept(type, buf.readDouble());
	}

}
