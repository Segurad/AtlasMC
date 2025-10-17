package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.function.Function;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class InnerTypeCompoundField<T, K> extends NBTField<T> {
	
	private final Function<T, ? super K> get;
	private final NBTCodec<K> handler;
	
	public InnerTypeCompoundField(CharSequence key, Function<T, ? super K> get, NBTCodec<K> handler) {
		super(key, COMPOUND, true);
		if (handler == null)
			throw new IllegalArgumentException("Handler can not be null!");
		this.handler = handler;
		this.get = get;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		Object raw = get.apply(value);
		if (!handler.getType().isInstance(raw))
			return false;
		@SuppressWarnings("unchecked")
		K v = (K) raw;
		writer.writeCompoundTag(key);
		handler.serialize(v, writer, context);
		writer.writeEndTag();
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		Object raw = get.apply(value);
		if (!handler.getType().isInstance(raw))
			throw new NBTException("Invalid type: " + raw.getClass() + " for handler type: " + handler.getType());
		@SuppressWarnings("unchecked")
		K v = (K) raw;
		reader.readNextEntry();
		handler.deserialize(v, reader, context);
	}

}
