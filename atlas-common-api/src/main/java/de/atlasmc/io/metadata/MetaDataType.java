package de.atlasmc.io.metadata;

import java.io.IOException;
import de.atlasmc.IDHolder;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

/**
 * Represents a {@link MetaData}s type
 * @param <T>
 */
public abstract class MetaDataType<T> implements IDHolder {
	
	public final int type;
	public final boolean optional;
	public final Class<?> clazz;
	
	public MetaDataType(int type, Class<?> typeClass) {
		this(type, typeClass, false);
	}
	
	public MetaDataType(int type, Class<?> typeClass, boolean optional) {
		this.type = type;
		this.clazz = typeClass;
		this.optional = optional;
	}
	
	@Override
	public int getID() {
		return type;
	}
	
	public Class<?> getTypeClass() {
		return clazz;
	}
	
	public boolean isOptional() {
		return optional;
	}
	
	/**
	 * Returns a copy of the data
	 * @param data
	 * @return copy
	 */
	public T copyData(T data) {
		return data;
	}
	
	public abstract T read(ByteBuf in, CodecContext context) throws IOException;
	
	public abstract void write(T data, ByteBuf out, CodecContext context) throws IOException;

	@SuppressWarnings("unchecked")
	public void writeRaw(Object data, ByteBuf buf, CodecContext context) throws IOException {
		write((T) data, buf, context);
	}

}
