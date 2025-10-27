package de.atlasmc.io.codec.field;

import java.io.IOException;

import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public abstract class StreamField<T> {

	/**
	 * Serializes a field of the given type
	 * @param type to serialize from
	 * @param buf to write to
	 * @param context used in this operation
	 * @return true of field was handled successfully
	 * @throws IOException
	 */
	public abstract boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException;
	
	/**
	 * Deserializes the field for the given type
	 * @param type to deserialize for
	 * @param buf to read from
	 * @param context used in this operation
	 * @throws IOException
	 */
	public abstract void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException;
	
}
