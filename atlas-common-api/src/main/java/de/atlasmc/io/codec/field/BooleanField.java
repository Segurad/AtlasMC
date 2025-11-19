package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ObjBooleanConsumer;
import de.atlasmc.util.function.ToBooleanFunction;
import io.netty.buffer.ByteBuf;

public class BooleanField<T> extends StreamField<T> {

	private final ToBooleanFunction<T> get;
	private final ObjBooleanConsumer<T> set;
	
	public BooleanField(ToBooleanFunction<T> get, ObjBooleanConsumer<T> set) {
		this.get = Objects.requireNonNull(get);
		this.set = Objects.requireNonNull(set);
	}
	
	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		buf.writeBoolean(get.applyAsBoolean(type));
		return true;
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		set.accept(type, buf.readBoolean());
	}

}
