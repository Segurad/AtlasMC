package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class IntField<T> extends StreamField<T> {

	private final ToIntFunction<T> get;
	private final ObjIntConsumer<T> set;
	
	public IntField(ToIntFunction<T> get, ObjIntConsumer<T> set) {
		this.get = Objects.requireNonNull(get);
		this.set = Objects.requireNonNull(set);
	}
	
	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		buf.writeInt(get.applyAsInt(type));
		return true;
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		set.accept(type, buf.readInt());
	}

}
