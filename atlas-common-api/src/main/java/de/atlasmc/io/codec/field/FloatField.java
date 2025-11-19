package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ToFloatFunction;
import io.netty.buffer.ByteBuf;

public class FloatField<T> extends StreamField<T> {

	private final ToFloatFunction<T> get;
	private final ObjFloatConsumer<T> set;
	
	public FloatField(ToFloatFunction<T> get, ObjFloatConsumer<T> set) {
		this.get = Objects.requireNonNull(get);
		this.set = Objects.requireNonNull(set);
	}
	
	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		buf.writeFloat(get.applyAsFloat(type));
		return true;
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		set.accept(type, buf.readFloat());
	}

}
