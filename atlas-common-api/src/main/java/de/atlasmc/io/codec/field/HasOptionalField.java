package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ToBooleanFunction;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.CodecException;

public class HasOptionalField<T> extends StreamField<T> {

	private final ToBooleanFunction<T> has;
	private final StreamField<T> field;
	
	public HasOptionalField(ToBooleanFunction<T> has, StreamField<T> field) {
		this.has = Objects.requireNonNull(has);
		this.field = Objects.requireNonNull(field);
	}
	
	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		if (has.applyAsBoolean(type)) {
			buf.writeBoolean(true);
			if (!field.serialize(type, buf, context))
				throw new CodecException("Field failed serialization!");
		} else {
			buf.writeBoolean(false);
		}
		return true;
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
