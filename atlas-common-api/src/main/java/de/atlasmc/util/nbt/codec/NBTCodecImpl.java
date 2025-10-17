package de.atlasmc.util.nbt.codec;

import java.io.IOException;
import java.util.function.Supplier;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.codec.constructor.Constructor;
import de.atlasmc.util.nbt.codec.type.NBTCompoundField;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class NBTCodecImpl<T> implements NBTCodec<T> {

	private final Class<T> type;
	private final boolean redirectAfterConstruction;
	private final Constructor<T> constructor;
	private final Supplier<T> defaultConstrcutor;
	private final NBTCompoundField<T> fields;
	
	public NBTCodecImpl(NBTCodecBuilder<T> builder) {
		this.type = builder.getType();
		this.redirectAfterConstruction = builder.isRedirectAfterConstruction();
		this.constructor = builder.getConstructor();
		this.defaultConstrcutor = builder.getDefaultConstructor();
		this.fields = builder.buildFields();
	}
	
	@Override
	public boolean serialize(T value, NBTWriter ouput, CodecContext context) throws IOException {
		if (redirectAfterConstruction && value instanceof NBTSerializable serializable) {
			@SuppressWarnings("unchecked")
			NBTCodec<T> handler = (NBTCodec<T>) serializable.getNBTCodec();
			if (handler != this) {
				return handler.serialize(value, ouput, context);
			}
		}
		if (constructor != null)
			constructor.serialize(value, ouput, context);
		if (fields != null)
			return fields.serialize(value, ouput, context);
		return true;
	}

	@Override
	public T deserialize(T value, NBTReader input, CodecContext context) throws IOException {
		if (value == null) {
			if (constructor != null)
				value = constructor.construct(input, context);
			if (value == null && defaultConstrcutor != null) {
				value = defaultConstrcutor.get();
				if (value == null)
					throw new NBTException("Unable to construct type!");
			}
		}
		if (redirectAfterConstruction && value instanceof NBTSerializable serializable) {
			@SuppressWarnings("unchecked")
			NBTCodec<T> handler = (NBTCodec<T>) serializable.getNBTCodec();
			if (handler != this) {
				return handler.deserialize(value, input, context);
			}
		}
		fields.deserialize(value, input, context);
		return value;
	}

	@Override
	public Class<T> getType() {
		return type;
	}

}
