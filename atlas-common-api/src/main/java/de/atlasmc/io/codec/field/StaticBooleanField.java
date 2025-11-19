package de.atlasmc.io.codec.field;

import java.io.IOException;

import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class StaticBooleanField<T> extends StreamField<T> {
	
	private static final StaticBooleanField<?> 
	TRUE_VALUE = new StaticBooleanField<>(true),
	FALSE_VALUE = new StaticBooleanField<>(false);
	
	private final boolean value;
	
	private StaticBooleanField(boolean value) {
		this.value = value;
	}
	
	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		buf.writeBoolean(value);
		return true;
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		buf.readBoolean();
	}
	
	public static <T> StaticBooleanField<T> valueOf(boolean value) {
		@SuppressWarnings("unchecked")
		StaticBooleanField<T> field = value ? (StaticBooleanField<T>) TRUE_VALUE : (StaticBooleanField<T>) FALSE_VALUE;
		return field;
	}

}
