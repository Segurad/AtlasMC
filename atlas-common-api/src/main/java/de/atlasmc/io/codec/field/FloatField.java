package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ToFloatFunction;
import io.netty.buffer.ByteBuf;

/**
 * Field implementation for primitive floats
 * @param <T>
 */
public final class FloatField<T> extends StreamField<T> {

	private final ToFloatFunction<T> get;
	private final ObjFloatConsumer<T> set;
	
	public FloatField(ToFloatFunction<T> get, ObjFloatConsumer<T> set) {
		this.get = Objects.requireNonNull(get, "get");
		this.set = Objects.requireNonNull(set, "set");
	}
	
	@Override
	public final boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		buf.writeFloat(get.applyAsFloat(type));
		return true;
	}

	@Override
	public final void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		set.accept(type, buf.readFloat());
	}

}
