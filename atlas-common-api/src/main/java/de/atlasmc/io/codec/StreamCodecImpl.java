package de.atlasmc.io.codec;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

import de.atlasmc.io.codec.constructor.Constructor;
import de.atlasmc.io.codec.field.StreamField;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.CodecException;

public class StreamCodecImpl<T> implements StreamCodec<T> {

	private final Class<?> clazz;
	private final Constructor<T> constructor;
	private final Supplier<T> defaultConstructor;
	private final StreamField<T>[] fields;
	
	@SuppressWarnings("unchecked")
	StreamCodecImpl(StreamCodecBuilder<T> builder) {
		this.clazz = Objects.requireNonNull(builder.getType());
		this.constructor = builder.getConstructor();
		this.defaultConstructor = builder.getDefaultConstructor();
		var fields = builder.getFields();
		this.fields = fields.toArray(new StreamField[fields.size()]);
		
	}
	
	@Override
	public boolean serialize(T value, ByteBuf output, CodecContext context) throws IOException {
		if (value instanceof StreamSerializable serializable) {
			@SuppressWarnings("unchecked")
			StreamCodec<T> handler = (StreamCodec<T>) serializable.getStreamCodec();
			if (handler != this) {
				return handler.serialize(value, output, context);
			}
		}
		if (constructor != null)
			constructor.serialize(value, output, context);
		if (fields != null) {
			for (var field : fields) {
				field.serialize(value, output, context);
			}
		}
		return true;
	}

	@Override
	public T deserialize(T value, ByteBuf input, CodecContext context) throws IOException {
		if (value == null) { // initialize value using constructor or default constructor
			if (constructor != null)
				value = constructor.construct(input, context);
			if (value == null && defaultConstructor != null) {
				value = defaultConstructor.get();
				if (value == null)
					throw new CodecException("Unable to construct type!");
			}
		}
		// redirect if required for correct deserialization of child implementations
		if (value instanceof StreamSerializable serializable) {
			@SuppressWarnings("unchecked")
			StreamCodec<T> handler = (StreamCodec<T>) serializable.getStreamCodec();
			if (handler != this) {
				return handler.deserialize(value, input, context);
			}
		}
		if (fields != null) {
			for (var field : fields) {
				field.deserialize(value, input, context);
			}
		}
		return value;
	}

	@Override
	public Class<?> getType() {
		return clazz;
	}

	StreamCodecBuilder<T> addToBuilder(StreamCodecBuilder<T> builder) {
		builder.defaultConstructor(defaultConstructor);
		builder.setConstructor(constructor);
		for (var field : fields) {
			builder.addField(field);
		}
		return builder;
	}

}
